with source as
    (
    select * from {{ ref('Daily_DataFrame') }}
    ),
renamed as
    (
    select
        "Movie_Title" as film_name,
        "Date" as date,
        "Daily" as earnings,
        "Theaters" as theaters,
        "Rank" as rank
    from source
    )

select * from renamed