with source as
    (
        select   hub_film_pk
                , getdate() as load_date
                , 'stg_movies' as load_source
                , distributor
        from (select distinct hash(film_name) as hub_film_pk, distributor from {{ ref('stg_movies') }}) s1
    )

select * from source
