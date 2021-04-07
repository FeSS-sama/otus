# Гид по безопасному Бостону

## Задание
В качестве исходных данных используется датасет https://www.kaggle.com/AnalyzeBoston/crimes-in-boston \
С помощью Spark соберите агрегат по районам (поле district) со следующими метриками:
* crimes_total - общее количество преступлений в этом районе
* crimes_monthly - медиана числа преступлений в месяц в этом районе
* frequent_crime_types - три самых частых crime_type за всю историю наблюдений в этом районе, объединенных через запятую с одним пробелом “, ” , расположенных в порядке убывания частоты
* crime_type - первая часть NAME из таблицы offense_codes, разбитого по разделителю “-” (например, если NAME “BURGLARY - COMMERICAL - ATTEMPT”, то crime_type “BURGLARY”)
* lat - широта координаты района, расчитанная как среднее по всем широтам инцидентов
* lng - долгота координаты района, расчитанная как среднее по всем долготам инцидентов

Программа должна упаковываться в uber-jar (с помощью sbt-assembly), и запускаться командой
```bash
spark-submit --master local[*] --class com.example.BostonCrimesMap /path/to/jar {path/to/crime.csv} {path/to/offense_codes.csv} {path/to/output_folder}
```
где {...} - аргументы, передаваемые пользователем.
Результатом её выполнения должен быть один файл в формате .parquet в папке path/to/output_folder.
Для джойна со справочником необходимо использовать broadcast.

### Решение 
uber-jar находится в корне проекта, - **Boston-guide-assembly-0.1.jar**. При желании можно пересобрать.\
Там же находятся и оригинальные файлы **crime.csv** и **offense_codes.csv**, которые использовались в этом проекте. 


Запуск
```bash
spark-submit --master local[*] --class main.BostonGuide /path/to/jar {path/to/crime.csv} {path/to/offense_codes.csv} {path/to/output_folder}
```