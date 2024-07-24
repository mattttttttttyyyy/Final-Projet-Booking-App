COMMENT ON SCHEMA public IS 'standard public schema';

CREATE TABLE public.booking (
    conference_room_id integer NOT NULL,
    end_time timestamp(6) without time zone,
    id integer NOT NULL,
    start_time timestamp(6) without time zone,
    unique_id character varying(255)
);

CREATE SEQUENCE public.booking_conference_room_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE public.booking_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE public.booking_seq
    START WITH 1
    INCREMENT BY 50;

CREATE TABLE public.conference_room (
    corporation_id integer,
    id integer NOT NULL,
    name character varying(20)
);

CREATE SEQUENCE public.conference_room_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE public.conference_room_seq
    START WITH 1
    INCREMENT BY 50;

CREATE TABLE public.corporation (
    id integer NOT NULL,
    name character varying(20)
);

CREATE TABLE public.corporation_conference_room_mapping (
    conference_room_id integer NOT NULL,
    corporation_id integer NOT NULL
);

CREATE SEQUENCE public.corporation_conference_room_mapping_conference_room_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE public.corporation_conference_room_mapping_corporation_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE public.corporation_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE public.corporation_seq
    START WITH 1
    INCREMENT BY 50;

insert into public.booking (conference_room_id, end_time, id, start_time, unique_id) values
(1,    '2023-07-20 12:33:00', 1, '2023-07-20 11:33:00', 'IWFYaFwHyF'),
(2,    '2023-07-20 12:33:00', 2, '2023-07-20 11:33:00', 'LzGYsWXeNS'),
(3,    '2023-07-20 12:33:00', 3, '2023-07-20 11:33:00', 'rnwsPwjuE7'),
(4,    '2023-07-20 12:33:00', 4, '2023-07-20 11:33:00', 'c5GYgDA7kU'),
(5,    '2023-07-20 12:33:00', 5, '2023-07-20 11:33:00', '4VdPhbBNtH');

insert into public.conference_room (corporation_id, id, name) values
(1,    1, 'conference room'),
(1,    2, 'conference room'),
(2, 3, 'conference room'),
(2,    4, 'conference room'),
(3,    5, 'conference room');

insert into public.corporation (id, name) values
(1,    'corporation 1'),
(2,    'corporation 2'),
(3,    'corporation 3');

ALTER TABLE public.booking
    ADD CONSTRAINT booking_pkey PRIMARY KEY (id);

ALTER TABLE public.conference_room
    ADD CONSTRAINT conference_room_pkey PRIMARY KEY (id);

ALTER TABLE public.corporation_conference_room_mapping
    ADD CONSTRAINT corporation_conference_room_mapping_pkey PRIMARY KEY (conference_room_id);

ALTER TABLE public.corporation
    ADD CONSTRAINT corporation_pkey PRIMARY KEY (id);

ALTER TABLE public.booking
    ADD CONSTRAINT fkd1g14hvidwhiuudxl4a67g12w FOREIGN KEY (conference_room_id) REFERENCES public.conference_room(id);


ALTER TABLE public.conference_room
    ADD CONSTRAINT fknm6prf166s1e3dmw88t70hc0k FOREIGN KEY (corporation_id) REFERENCES public.corporation(id);