--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

-- Started on 2025-07-24 16:27:11

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 16475)
-- Name: clientes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clientes (
    id bigint NOT NULL,
    identification_type character varying(20) NOT NULL,
    identification_number character varying(20) NOT NULL,
    names character varying(100) NOT NULL,
    email character varying(100),
    cellphone character varying(20)
);


ALTER TABLE public.clientes OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16474)
-- Name: clientes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.clientes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.clientes_id_seq OWNER TO postgres;

--
-- TOC entry 4811 (class 0 OID 0)
-- Dependencies: 217
-- Name: clientes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.clientes_id_seq OWNED BY public.clientes.id;


--
-- TOC entry 220 (class 1259 OID 16484)
-- Name: direcciones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.direcciones (
    id bigint NOT NULL,
    cliente_id bigint NOT NULL,
    provincia character varying(100) NOT NULL,
    ciudad character varying(100) NOT NULL,
    direccion character varying(200) NOT NULL,
    es_matriz boolean DEFAULT false NOT NULL
);


ALTER TABLE public.direcciones OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16483)
-- Name: direcciones_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.direcciones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.direcciones_id_seq OWNER TO postgres;

--
-- TOC entry 4812 (class 0 OID 0)
-- Dependencies: 219
-- Name: direcciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.direcciones_id_seq OWNED BY public.direcciones.id;


--
-- TOC entry 4646 (class 2604 OID 16478)
-- Name: clientes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes ALTER COLUMN id SET DEFAULT nextval('public.clientes_id_seq'::regclass);


--
-- TOC entry 4647 (class 2604 OID 16487)
-- Name: direcciones id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.direcciones ALTER COLUMN id SET DEFAULT nextval('public.direcciones_id_seq'::regclass);


--
-- TOC entry 4803 (class 0 OID 16475)
-- Dependencies: 218
-- Data for Name: clientes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clientes (id, identification_type, identification_number, names, email, cellphone) FROM stdin;
2	RUC	0505555555001	Marcelo Sandoval	marcelo@test.com	0999999999
3	DNI	0606666666	Andrea Paredes	andrea@test.com	0988888888
1	CED	1234567890	Daniel Sandoval editado	nuevoemail@gmail.com	0922222222
\.


--
-- TOC entry 4805 (class 0 OID 16484)
-- Dependencies: 220
-- Data for Name: direcciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.direcciones (id, cliente_id, provincia, ciudad, direccion, es_matriz) FROM stdin;
1	1	Cotopaxi	Salcedo	Calle Ana paredes	t
2	2	Cotopaxi	Latacunga	El salto	t
3	3	Pichincha	Quito	Av. Amazonas y Naciones Unidas	t
5	1	Manabí	Manta	Av. Malecón	f
6	1	Manabí	Manta	Av. Malecón	f
7	1	Tungurahua	Ambato	Av. El rey	f
\.


--
-- TOC entry 4813 (class 0 OID 0)
-- Dependencies: 217
-- Name: clientes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.clientes_id_seq', 4, true);


--
-- TOC entry 4814 (class 0 OID 0)
-- Dependencies: 219
-- Name: direcciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.direcciones_id_seq', 7, true);


--
-- TOC entry 4650 (class 2606 OID 16482)
-- Name: clientes clientes_identification_number_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_identification_number_key UNIQUE (identification_number);


--
-- TOC entry 4652 (class 2606 OID 16480)
-- Name: clientes clientes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_pkey PRIMARY KEY (id);


--
-- TOC entry 4654 (class 2606 OID 16490)
-- Name: direcciones direcciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.direcciones
    ADD CONSTRAINT direcciones_pkey PRIMARY KEY (id);


--
-- TOC entry 4655 (class 1259 OID 16496)
-- Name: unico_cliente_matriz; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX unico_cliente_matriz ON public.direcciones USING btree (cliente_id) WHERE (es_matriz = true);


--
-- TOC entry 4656 (class 2606 OID 16491)
-- Name: direcciones direcciones_cliente_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.direcciones
    ADD CONSTRAINT direcciones_cliente_id_fkey FOREIGN KEY (cliente_id) REFERENCES public.clientes(id) ON DELETE CASCADE;


-- Completed on 2025-07-24 16:27:11

--
-- PostgreSQL database dump complete
--

