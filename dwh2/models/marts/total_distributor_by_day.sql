with source as
    (
        select  date
                , distributor
                , sum(earnings) as total
        from {{ ref('sat_days_details') }}
        join {{ ref('hub_days') }} using (hub_days_pk)
        join {{ ref('link_films_daily') }} using (hub_days_pk)
        join {{ ref('hub_film') }} using (hub_film_pk)
        join {{ ref('sat_film_distributor') }} using (hub_film_pk)
        group by date
                , distributor
    )

select * from source
order by total desc