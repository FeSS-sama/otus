# Приложение для чтения JSON-файлов

## Задание
Напишите приложение, которое читает json-файл с помощью Spark RDD API 
без использования Dataframe/Dataset (https://storage.googleapis.com/otus_sample_data/winemag-data.json.tgz),  
конвертирует его содержимое в case class’ы и распечатывает их в stdout.
Расположение файла передается первым и единственным аргументом.
#### Сборка и запуск приложения
1. Главный класс приложения должен называться JsonReader
2. Собрать приложение можно с помощью команды sbt assembly
3. Для запуска приложения через спарк нужно скачать Spark (версия 2.4.x, scala 2.11) - https://spark.apache.org/downloads.html
4. Приложение запускается командой
```bash
/path/to/spark/bin/spark-submit --master local[*] --class com.example.JsonReader /path/to/assembly-jar {path/to/winemag.json}
```
## Решение 
uber-jar находится в корне проекта, - **json_reader_nikolaev-assembly-0.1.jar**. При желании можно пересобрать.\
Там же находятся и оригинальный файл **winemag-data-130k-v2.json**, который использовался в этом проекте. 


Запуск
```bash
spark-submit --master local[*] --class main.JsonReader /path/to/jar {path/to/winemag.json}
```