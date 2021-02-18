CREATE TABLE if not exists public.holder
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    birth_date date,
    city character varying(255) COLLATE pg_catalog."default",
    country character varying(255) COLLATE pg_catalog."default",
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    street character varying(255) COLLATE pg_catalog."default",
    zip_code character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT holder_pkey PRIMARY KEY (id)
);

CREATE TABLE if not exists public.account
(
    uid character varying(255) COLLATE pg_catalog."default" NOT NULL,
    type character varying(255) COLLATE pg_catalog."default",
    balance double precision,
    holder_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (uid),
    CONSTRAINT fk_holder_id FOREIGN KEY (holder_id)
        REFERENCES public.holder (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE if not exists public.transfer
(
    account_id_from character varying(255) COLLATE pg_catalog."default" NOT NULL,
    account_id_to character varying(255) COLLATE pg_catalog."default" NOT NULL,
    amount double precision,
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    execution_date timestamp without time zone,
    CONSTRAINT transfer_pkey PRIMARY KEY (id)
);

CREATE OR REPLACE FUNCTION fonction_pkTransfet_save() RETURNS TRIGGER AS $$
    BEGIN
        IF NEW.account_id_from is NULL OR NEW.account_id_to is NULL THEN
            RAISE EXCEPTION 'account_id_from ou account_id_to est NULL';
        ELSE
            PERFORM uid FROM account WHERE uid=NEW.account_id_from;
            IF NOT FOUND THEN
                RAISE EXCEPTION E'account_id_from n\'existe pas';
            END IF;
            PERFORM uid FROM account WHERE uid=NEW.account_id_to;
            IF NOT FOUND THEN
                RAISE EXCEPTION E'account_id_to n\'existe pas';
            END IF;
        END IF;
    RETURN new;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trig_pkTransfer_save
    BEFORE INSERT OR UPDATE
    OF account_id_from, account_id_to
    ON transfer
    FOR EACH ROW
    EXECUTE PROCEDURE fonction_pkTransfet_save();

CREATE OR REPLACE FUNCTION fonction_pkTransfet_delete() RETURNS TRIGGER AS $$
    BEGIN
        IF OLD.uid is NULL THEN
            RAISE EXCEPTION 'uid est NULL';
        ELSE
            PERFORM account_id_from FROM transfer WHERE account_id_from=OLD.uid;
            IF FOUND THEN
                DELETE FROM transfer where account_id_from=OLD.uid;
            END IF;
            PERFORM account_id_to FROM transfer WHERE account_id_to=OLD.uid;
            IF FOUND THEN
                DELETE FROM transfer where account_id_to=OLD.uid;
            END IF;
        END IF;
    RETURN old;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trig_pkTransfer_delete
    BEFORE DELETE
    ON account
    FOR EACH ROW
    EXECUTE PROCEDURE fonction_pkTransfet_delete();