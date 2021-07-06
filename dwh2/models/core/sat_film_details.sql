with source as
    (
    select  hash(film_name) as hub_days_pk
            , 'stg_movies' as load_source
            , getdate() as load_date
            , budget
            , genres
            , runtime
            , mpaa_rating
    from {{ ref('stg_movies') }}
    )

select * from source