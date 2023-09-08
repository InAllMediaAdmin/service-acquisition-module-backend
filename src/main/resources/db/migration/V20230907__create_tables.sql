CREATE TABLE IF NOT EXISTS hibernate_sequence
(
    sequence_name         VARCHAR(255)       NOT NULL,
    next_not_cached_value BIGINT(21)         NOT NULL DEFAULT 1,
    minimum_value         BIGINT(21)         NOT NULL DEFAULT 1,
    maximum_value         BIGINT(21)         NOT NULL DEFAULT 9223372036854775807,
    start_value           BIGINT(21)         NOT NULL DEFAULT 1 COMMENT 'start value when sequences is created or value if RESTART is used',
    increment             BIGINT(21)         NOT NULL DEFAULT 1 COMMENT 'increment value',
    cache_size            BIGINT(21) UNSIGNED NOT NULL DEFAULT 1,
    cycle_option          TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
    cycle_count           BIGINT(21)         NOT NULL DEFAULT 0 COMMENT 'How many cycles have been done',
    PRIMARY KEY (sequence_name)
    );

INSERT INTO hibernate_sequence
(sequence_name, next_not_cached_value, minimum_value, maximum_value, start_value, increment, cache_size, cycle_option, cycle_count)
VALUES
    ('acquisition_seq', 1, 1, 9223372036854775807, 1, 1, 1, 0, 0);

create table if not exists company
(
    id        bigint       not null
    primary key,
    name      varchar(255) not null,
    client_id bigint       null
    );

create table if not exists country
(
    id   bigint       not null
    primary key,
    name varchar(255) not null
    );

create table if not exists industry
(
    id          bigint       not null
    primary key,
    description varchar(255) null
    );

create table if not exists phone
(
    id           bigint       not null
    primary key,
    area_code    varchar(255) null,
    country_code varchar(255) null,
    phone_number varchar(255) null
    );

create table if not exists search_filter
(
    id                        bigint        not null
    primary key,
    english_must              bit           null,
    english_value             int           null,
    experience_must           bit           null,
    experience_value          int           null,
    industry_must             bit           null,
    industry_value            bigint        null,
    rate_must                 bit           null,
    rate_per_hour             decimal(5, 2) null,
    secondary_technology_must bit           null,
    strategy_must             bit           null,
    years_working_must        bit           null,
    years_working_value       int           null
    );

create table if not exists combo_structure
(
    id        bigint not null
    primary key,
    filter_id bigint null,
    constraint FKd1q1x6sbtcgknox08tpy07wlp
    foreign key (filter_id) references search_filter (id)
    );

create table if not exists search_strategies_values
(
    search_filter_id  bigint not null,
    strategies_values bigint null,
    constraint FK3w4u82q8kfsasfyeohufnm6hm
    foreign key (search_filter_id) references search_filter (id)
    );

create table if not exists search_talent_filter_secondary_technologies
(
    search_talent_filter_id   bigint not null,
    secondary_technologies_id bigint not null
);

create table if not exists search_talent_filter_strategies
(
    search_talent_filter_id bigint not null,
    strategies_id           bigint not null
);

create table if not exists stakeholder
(
    id         bigint       not null
    primary key,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    email      varchar(255) not null,
    phone_id   bigint       null,
    role       varchar(255) null,
    client_id  bigint       null,
    constraint FK_stakeholder_phone
    foreign key (phone_id) references phone (id)
    );

create table if not exists strategy
(
    id          bigint       not null
    primary key,
    description varchar(255) null
    );

create table if not exists talent_degree_certification
(
    id          bigint       not null
    primary key,
    description varchar(255) null
    );

create table if not exists talent_degree_education
(
    id          bigint       not null
    primary key,
    description varchar(255) null
    );

create table if not exists talent_profit
(
    id    bigint         not null
    primary key,
    value decimal(19, 2) null,
    date  datetime       null
    );

create table if not exists talent_resume_highlight_strategy
(
    id bigint not null
    primary key
);

create table if not exists talent_resume_highlight
(
    id                    bigint not null
    primary key,
    highlight_strategy_id bigint null,
    constraint FKmrw590so01ikrhs31kkd1uxf6
    foreign key (highlight_strategy_id) references talent_resume_highlight_strategy (id)
    );

create table if not exists talent_resume_highlight_strategy_strategies
(
    talent_resume_highlight_strategy_id bigint not null,
    strategies_id                       bigint not null,
    constraint FKco2901w9jg5hu6agr3dvq435q
    foreign key (strategies_id) references strategy (id),
    constraint FKnqh7gf6yhaicacgvylnr7m0rt
    foreign key (talent_resume_highlight_strategy_id) references talent_resume_highlight_strategy (id)
    );

create table if not exists team_position
(
    id             bigint       not null
    primary key,
    position_index bigint       null,
    name           varchar(255) null
    );

create table if not exists technology
(
    id          bigint       not null
    primary key,
    description varchar(255) null
    );

create table if not exists technology_roles
(
    technology_id   bigint       not null,
    roles           varchar(255) null,
    default_role_id bigint       not null,
    constraint FK2urnf3ofm4kbldg44xag3hfck
    foreign key (technology_id) references technology (id)
    );

create table if not exists time_zone
(
    id                 bigint        not null
    primary key,
    long_description   varchar(255)  null,
    medium_description varchar(255)  null,
    short_description  varchar(255)  null,
    time_zone_offset   decimal(4, 2) null,
    country            varchar(255)  null
    );

create table if not exists adhoc_city
(
    id           bigint       not null
    primary key,
    city         varchar(255) null,
    time_zone_id bigint       null,
    constraint FKsy4x3j1hdrdktn6idq0qhnswr
    foreign key (time_zone_id) references time_zone (id)
    );

create table if not exists customer_lead
(
    id                bigint       not null
    primary key,
    lead_project_name varchar(255) null,
    industry_id       bigint       null,
    location          varchar(255) null,
    time_zone_id      bigint       null,
    additional_notes  text         null,
    client_partner_id bigint       null,
    stakeholder_id    bigint       null,
    company_id        bigint       null,
    created           datetime     null,
    constraint FK_customer_lead_company
    foreign key (company_id) references company (id),
    constraint FK_customer_lead_industry_id
    foreign key (industry_id) references industry (id),
    constraint FK_customer_lead_stakeholder
    foreign key (stakeholder_id) references stakeholder (id),
    constraint FK_customer_lead_time_zone_id
    foreign key (time_zone_id) references time_zone (id)
    );

create table if not exists client_partner_request
(
    id                   bigint       not null
    primary key,
    title                varchar(255) null,
    client_partner_id    bigint       null,
    industry_id          bigint       null,
    status               varchar(255) null,
    comments             text         null,
    updated_at           datetime(6)  not null,
    min_team_composition int          null,
    max_team_composition int          null,
    time_zone_id         bigint       null,
    blended_hourly_rate  varchar(255) null,
    customer_lead_id     bigint       null,
    pre_sales_user_id    bigint       null,
    constraint FK1eu1lsdx1sh1fvsq1jhsimok
    foreign key (customer_lead_id) references customer_lead (id),
    constraint FK41qar6n8jat7vlr6u44qim4ku
    foreign key (industry_id) references industry (id),
    constraint FK_cp_request_time_zone
    foreign key (time_zone_id) references time_zone (id)
    );

create table if not exists client_partner_request_technologies
(
    client_partner_request_id bigint not null,
    technology_id             bigint not null,
    constraint FK_cp_request_technologies_cp_request
    foreign key (client_partner_request_id) references client_partner_request (id),
    constraint FK_cp_request_technologies_technology
    foreign key (technology_id) references technology (id)
    );

create table if not exists search_request
(
    id           bigint                                not null
    primary key,
    start_date   date                                  null,
    time_zone_id bigint                                null,
    filter_id    bigint                                null,
    created      timestamp default current_timestamp() not null on update current_timestamp(),
    user         bigint    default 1                   not null,
    constraint FKhd3jrccohvo44d9qp1yoyb2h9
    foreign key (time_zone_id) references time_zone (id),
    constraint FKjeovit2mxk1w1t9p0yvxuk7s7
    foreign key (filter_id) references search_filter (id)
    );

create table if not exists search_position_slot
(
    id                 bigint       not null
    primary key,
    matching           int          not null,
    role               varchar(255) null,
    default_role_id    bigint       not null,
    seniority          varchar(255) null,
    position_slot_type varchar(255) null,
    search_request_id  bigint       null,
    combo_id           bigint       null,
    constraint FK630lkljgqw5tjhx8w1kh9nfhd
    foreign key (search_request_id) references search_request (id),
    constraint FKl4b9qhds1yau9h2ujs5l2wg69
    foreign key (combo_id) references combo_structure (id)
    );

create table if not exists search_position_slot_main_technologies
(
    search_position_slot_id bigint not null,
    main_technologies_id    bigint not null,
    constraint FK174ihk0sajg9emqwvultvhecg
    foreign key (search_position_slot_id) references search_position_slot (id),
    constraint FK9n2a27udkbk53v4hamn1uqeaw
    foreign key (main_technologies_id) references technology (id)
    );

create table if not exists search_position_slot_secondary_technologies
(
    search_position_slot_id   bigint not null,
    secondary_technologies_id bigint not null,
    constraint FK1y9fdm065h6115l7fuhe3amxa
    foreign key (secondary_technologies_id) references technology (id),
    constraint FK4cbxiharfko123ratf1wrpc7t
    foreign key (search_position_slot_id) references search_position_slot (id)
    );

create table if not exists search_talent
(
    id                      bigint not null
    primary key,
    availability            date   null,
    user                    bigint not null,
    search_talent_filter_id bigint null,
    search_filter_id        bigint null,
    time_zone_id            bigint null,
    constraint FK7u2b604xdydepxw8sqinvmxmc
    foreign key (search_filter_id) references search_filter (id),
    constraint FKi13p644xxcnum2ubjkqnaahmx
    foreign key (time_zone_id) references time_zone (id)
    );

create index if not exists FK96fgcyon9s9jsj6sx4va0wmcf
    on search_talent (user);

create table if not exists search_talent_roles
(
    role             int    not null,
    quantity         int    null,
    id_search_talent bigint not null,
    primary key (role, id_search_talent),
    constraint FKl8mifrq53l78dymq22pepdimx
    foreign key (id_search_talent) references search_talent (id)
    );

create table if not exists search_talent_refinement
(
    id                                   bigint       not null
    primary key,
    seniority                            varchar(255) not null,
    search_talent_roles_role             int          null,
    search_talent_roles_id_search_talent bigint       null,
    main_technology                      bigint       not null,
    constraint FKlrkwwsw55qocsbuae21q0pawd
    foreign key (search_talent_roles_role, search_talent_roles_id_search_talent) references search_talent_roles (role, id_search_talent),
    constraint FKmnsqnyr70gt6dg8t35v9acd8m
    foreign key (main_technology) references technology (id)
    );

create table if not exists team
(
    id                bigint         not null
    primary key,
    description       varchar(255)   null,
    availability_date date           null,
    matching          int            not null,
    rate              decimal(19, 2) null,
    search_request_id bigint         null,
    time_zone_id      bigint         null,
    team_logo         varchar(255)   null,
    team_name         varchar(255)   null,
    constraint FKl0hp4e09xufm4ywr37gs8301b
    foreign key (time_zone_id) references time_zone (id),
    constraint FKl4b5qhds1yau9h2ujs5l2wg69
    foreign key (search_request_id) references search_request (id)
    );

create table if not exists talent
(
    id                         bigint               not null
    primary key,
    availability_date          date                 null,
    city                       varchar(255)         null,
    seniority                  varchar(255)         null,
    team_id                    bigint               null,
    last_name                  varchar(255)         null,
    name                       varchar(255)         null,
    rate_per_hour              decimal(5, 2)        null,
    gender                     varchar(255)         null,
    is_fake                    bit                  null,
    english_level              varchar(255)         null,
    experience                 double               null,
    worked_with_us             int                  null,
    avatar                     varchar(255)         null,
    state                      varchar(255)         null,
    time_zone_id               bigint               null,
    email                      varchar(255)         null,
    profile_linkedin           varchar(255)         null,
    phone_id                   bigint               null,
    printable_name             varchar(255)         null,
    last_update                datetime             null,
    email_iam                  varchar(255)         null,
    birth_date                 date                 null,
    country_id                 bigint               null,
    profile_description        text                 null,
    interest_description       text                 null,
    created_at                 datetime             null,
    gpt_ia                     tinyint(1) default 0 not null,
    created_in_service_manager tinyint(1) default 0 not null,
    constraint FK7glhr9cjob8ijmu8g76njtu7f
    foreign key (team_id) references team (id),
    constraint FK_country_id
    foreign key (country_id) references country (id),
    constraint FKdp7ivwr5i8h283nl08j0v6ubs
    foreign key (time_zone_id) references time_zone (id),
    constraint FKtq7nfowdc31ti35gjf2e0397y
    foreign key (phone_id) references phone (id)
    );

create table if not exists talent_certification
(
    id         bigint       not null
    primary key,
    degree_id  varchar(255) null,
    end_date   date         null,
    school     varchar(255) null,
    start_date date         null,
    talent_id  bigint       null,
    constraint FKre94lt18tuxkty5q95rsdthts
    foreign key (talent_id) references talent (id)
    );

create table if not exists talent_education
(
    id         bigint       not null
    primary key,
    end_date   date         null,
    school     varchar(255) null,
    start_date date         null,
    talent_id  bigint       null,
    degree_id  bigint       null,
    constraint FKpq8uxw3xpcn7649p0sx54js13
    foreign key (talent_id) references talent (id),
    constraint FKsa076rtiottfgc75p9ldjae4s
    foreign key (degree_id) references talent_degree_education (id)
    );

create table if not exists talent_experience
(
    id                    bigint           not null
    primary key,
    activities            text             null,
    company               varchar(255)     null,
    description           text             null,
    end_date              date             null,
    role                  varchar(255)     null,
    start_date            date             null,
    industry_id           bigint           null,
    main_technology       bigint           null,
    talent_id             bigint           null,
    main_technology_years double           null,
    iam_experience        bit              null,
    remote_work           bit              null,
    currently             bit default b'1' null,
    constraint FK1c63k0jv9pjwyymqyti37dg9w
    foreign key (talent_id) references talent (id),
    constraint FK71w8kkx18chmlbmfl3325o18l
    foreign key (industry_id) references industry (id),
    constraint FKa1oelgipf4fmdqod9elattj3x
    foreign key (main_technology) references technology (id)
    );

create table if not exists talent_experience_additional_technologies
(
    talent_experience_id       bigint not null,
    additional_technologies_id bigint not null,
    constraint FKiklk0p1n7g0ap4etv6coqrc6f
    foreign key (additional_technologies_id) references technology (id),
    constraint FKlyff06hojaxr3ost8gq7hjqji
    foreign key (talent_experience_id) references talent_experience (id)
    );

create table if not exists talent_highlight
(
    id                   bigint not null
    primary key,
    talent_id            bigint not null,
    title                text   not null,
    subtitle             text   not null,
    description          text   not null,
    talent_experience_id bigint not null,
    constraint fk_talent_highlight_talent_experience_id
    foreign key (talent_experience_id) references talent_experience (id),
    constraint talent_highlight_ibfk_1
    foreign key (talent_id) references talent (id)
    );

create index if not exists talent_id
    on talent_highlight (talent_id);

create table if not exists talent_industry_expertise
(
    id          bigint       not null
    primary key,
    description varchar(255) null,
    years       int          not null,
    industry_id bigint       null,
    talent_id   bigint       null,
    constraint FK5cyir5dlhi7gjydaaam547rud
    foreign key (industry_id) references industry (id),
    constraint FKcrwx13nr219w0tdlcm92dt0ps
    foreign key (talent_id) references talent (id)
    );

create table if not exists talent_language_certification
(
    id            bigint       not null
    primary key,
    certification varchar(255) null,
    institution   varchar(255) null,
    year          int          not null,
    talent_id     bigint       null,
    constraint FKqphyrebswmy2rt834j3migl19
    foreign key (talent_id) references talent (id)
    );

create table if not exists talent_main_technologies
(
    talent_id            bigint not null,
    main_technologies_id bigint not null,
    constraint FK5eu4lsdx8sh3fvsq6jhsymak
    foreign key (talent_id) references talent (id),
    constraint FKcgmfs0ejxvm1fbyqhd8ja80f9
    foreign key (main_technologies_id) references technology (id)
    );

create table if not exists talent_roles
(
    talent_id       bigint       not null,
    roles           varchar(255) null,
    default_role_id bigint       not null,
    constraint FKba04kf24apllh9q47kdiia8y9
    foreign key (talent_id) references talent (id)
    );

create table if not exists talent_secondary_technologies
(
    talent_id                 bigint not null,
    secondary_technologies_id bigint not null,
    constraint FK1ckn46pawrueaghbmawal23y6
    foreign key (secondary_technologies_id) references technology (id),
    constraint FKqqd9x3xj5gloh0ivm9woywghk
    foreign key (talent_id) references talent (id)
    );

create table if not exists talent_soft_skill
(
    id                   bigint       not null
    primary key,
    description          text         null,
    name_person          varchar(255) null,
    role_person          varchar(255) null,
    strategy_id          bigint       null,
    talent_id            bigint       null,
    talent_experience_id bigint       null,
    constraint FKiyvdfhk9nnkhg74ukkoyo343c
    foreign key (strategy_id) references strategy (id),
    constraint FKlnk7m0xxiiye441ggkk8033ju
    foreign key (talent_id) references talent (id),
    constraint fk_talent_experience_id
    foreign key (talent_experience_id) references talent_experience (id)
    );

create table if not exists talent_technical_profiles
(
    id              bigint       not null
    primary key,
    talent_id       bigint       not null,
    role            varchar(255) not null,
    default_role_id bigint       not null,
    seniority       varchar(255) not null,
    current_profile bit          not null,
    constraint talent_technical_profiles_ibfk_1
    foreign key (talent_id) references talent (id)
    );

create index if not exists talent_id
    on talent_technical_profiles (talent_id);

create table if not exists team_proposal
(
    id                bigint        not null
    primary key,
    blended_rate      decimal(5, 2) null,
    created           datetime(6)   null,
    team_overview     text          null,
    proposal_uuid     varchar(255)  null,
    last_update       datetime(6)   null,
    time_zone_id      bigint        null,
    availability_date date          null,
    constraint FKl0hp4e10pafm4ywr37gs8301b
    foreign key (time_zone_id) references time_zone (id)
    );

create table if not exists team_request
(
    id                            bigint       not null
    primary key,
    contact_name                  varchar(255) null,
    due_date                      date         null,
    lead_name                     varchar(255) null,
    requested_date                date         null,
    talent_agent_id               bigint       null,
    team_id                       bigint       null,
    client_partner_id             bigint       null,
    status                        varchar(255) null,
    additional_information        text         null,
    short_client_need_description text         null,
    long_client_need_description  text         null,
    lead_project_name             varchar(255) null,
    team_proposal_id              bigint       null,
    modal_proposal_status         text         null,
    customer_lead_id              bigint       null,
    constraint FK_team_request_customer_lead_id
    foreign key (customer_lead_id) references customer_lead (id),
    constraint FKs5sofls1sjvq00n9lfb2kyxm6
    foreign key (team_proposal_id) references team_proposal (id),
    constraint FKtctko3jheeky34b3lj7kmmwij
    foreign key (team_id) references team (id)
    );

create table if not exists due_date
(
    id              bigint       not null
    primary key,
    date            date         null,
    team_request_id bigint       null,
    status          varchar(255) null,
    constraint FKbi4je5b7c982g4r5nwr5083sq
    foreign key (team_request_id) references team_request (id)
    );

create table if not exists talent_resume
(
    id                bigint       not null
    primary key,
    interests         text         null,
    seniority         varchar(255) not null,
    name              varchar(255) null,
    profile_summary   text         null,
    role              varchar(255) null,
    default_role_id   bigint       null,
    talent_id         bigint       null,
    team_request_id   bigint       null,
    avatar            varchar(255) null,
    real_name         varchar(255) null,
    state             varchar(255) null,
    highlight_id      bigint       null,
    adhoc             bit          null,
    english_level     varchar(255) null,
    availability_date date         null,
    constraint FK7gcdurwxuggur8m9qccsbj3ba
    foreign key (talent_id) references talent (id),
    constraint FKfmhdrks7kikut3cmg8l17rew2
    foreign key (highlight_id) references talent_resume_highlight (id),
    constraint FKqrj5tvlu1dkhqv6qgdsudsthp
    foreign key (team_request_id) references team_request (id)
    );

create table if not exists talent_resume_certifications
(
    talent_resume_id  bigint not null,
    certifications_id bigint not null,
    constraint FK7k3dknxcc4trr68k9310tl4sv
    foreign key (certifications_id) references talent_certification (id),
    constraint FKjxxcwaqs3t0hcwnavpk3d12oq
    foreign key (talent_resume_id) references talent_resume (id)
    );

create table if not exists talent_resume_educations
(
    talent_resume_id bigint not null,
    educations_id    bigint not null,
    constraint FKq2couryobh4te02uwyedui355
    foreign key (educations_id) references talent_education (id),
    constraint FKtmwubws4v71j26lfx7m2cnxve
    foreign key (talent_resume_id) references talent_resume (id)
    );

create table if not exists talent_resume_experiences
(
    talent_resume_id bigint not null,
    experiences_id   bigint not null,
    constraint FKe08jfp1kbbu3nj3fwijnpairy
    foreign key (experiences_id) references talent_experience (id),
    constraint FKgcs6il2nw3jbe0qg3inmgn4p4
    foreign key (talent_resume_id) references talent_resume (id)
    );

create table if not exists talent_resume_industry_expertises
(
    talent_resume_id       bigint not null,
    industry_expertises_id bigint not null,
    constraint FK6schb7jum3l9jwhpyln9ih6bj
    foreign key (talent_resume_id) references talent_resume (id),
    constraint FKdp9xauqa4vpkqsua7p3g49597
    foreign key (industry_expertises_id) references talent_industry_expertise (id)
    );

create table if not exists talent_resume_language_certifications
(
    talent_resume_id           bigint not null,
    language_certifications_id bigint null,
    constraint FKck9h0jelr138cukssnwi2c3p1
    foreign key (language_certifications_id) references talent_language_certification (id),
    constraint FKqp5101de72j2yu04yj43s1oaa
    foreign key (talent_resume_id) references talent_resume (id)
    );

create table if not exists talent_resume_soft_skills
(
    talent_resume_id bigint not null,
    soft_skills_id   bigint not null,
    constraint FKc4gnmqfcci0t2vfi6qrvj1dxm
    foreign key (soft_skills_id) references talent_soft_skill (id),
    constraint FKclgw6fdk3ip39pamhcqrt99fy
    foreign key (talent_resume_id) references talent_resume (id)
    );

create table if not exists team_position_slot
(
    id               bigint not null
    primary key,
    position_slot_id bigint null,
    talent_id        bigint null,
    talent_resume_id bigint null,
    team_id          bigint null,
    constraint FKhl95rmf88pf6mjkngp7o8qufj
    foreign key (talent_resume_id) references talent_resume (id),
    constraint FKj87lwsps0m2lga0980vrs3ldi
    foreign key (team_id) references team (id),
    constraint FKoh7aviucl6lny4xqd2xwnj139
    foreign key (talent_id) references talent (id),
    constraint FKqaeruu4e1n4muvk8vq0kr8cd9
    foreign key (position_slot_id) references search_position_slot (id)
    );

create table if not exists activity
(
    id                        bigint                                not null
    primary key,
    type                      varchar(255)                          not null,
    talent_id                 bigint                                null,
    author_id                 bigint                                not null,
    created                   timestamp default current_timestamp() not null on update current_timestamp(),
    team_position_slot_id     bigint                                null,
    team_request_id           bigint                                null,
    client_partner_request_id bigint                                null,
    constraint FK49pcn8r900vy0sv1yma09h0tb
    foreign key (team_request_id) references team_request (id),
    constraint FK65gv6wtmdvpyf48uqebg9cz03
    foreign key (talent_id) references talent (id),
    constraint FK_activity_cp_request
    foreign key (client_partner_request_id) references client_partner_request (id),
    constraint FKbd3idxs6qe63isq9flsww3dvy
    foreign key (team_position_slot_id) references team_position_slot (id)
    );

create index if not exists FK9vtn4x1xk8cwb4s4pvfqz5l19
    on activity (author_id);

create table if not exists comment
(
    id          bigint           not null
    primary key,
    body        text             not null,
    attachment  varchar(255)     null,
    activity_id bigint           not null,
    read_by_cp  bit default b'0' not null,
    read_by_tm  bit default b'0' not null,
    constraint FK4b1wtk4o1wy7pqjspmgfa4a6o
    foreign key (activity_id) references activity (id)
    );

create index if not exists FKevv5sma7csybcjoldpjwuuf5s
    on team_request (client_partner_id);

create index if not exists FKgge0lsv1itf8u2544wo4h5von
    on team_request (talent_agent_id);

create table if not exists user_profile
(
    id          bigint           not null
    primary key,
    has_request bit default b'1' not null
);

create table if not exists user_profile_roles
(
    user_profile_id bigint       not null,
    roles           varchar(255) null,
    constraint FKt1rrgpxb7w93li06xbr0oqxo7
    foreign key (user_profile_id) references user_profile (id)
    );