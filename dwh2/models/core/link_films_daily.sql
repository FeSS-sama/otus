with source as
    (
        select  hash(hash(s2.film_name, date), hash(s1.film_name)) as link_film_daily_pk
                , hash(s2.film_name, date) as hub_days_pk
                , hash(s1.film_name) as hub_film_pk
                , 'stg_movies_join_stg_daily_data' as load_source
                , getdate() as load_date
        from (select distinct film_name from {{ ref('stg_movies') }}) s1
        left join {{ ref('stg_daily_data') }} s2 using (film_name)
    )

select * from source