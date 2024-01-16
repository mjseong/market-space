# market-space

## Model Design
### 원본 데이터의 분석
데이터의 정보는 4개의 상권 그룹, 1649개의 상권 코드, 100개의 서비스업종으로 구성되어 있습니다.

해당 데이터를 분리하여 정규화 한다면 아래와 같은 값으로 나눌수 있습니다.
* 상권의 해당년도 분기마다 데이터 값 
* 상권의 그룹 정보 
* 상권의 기초 정보
* 서비스 업종의 기초 정보

*Raw Data*
![markspace-raw-data.png](document%2Fmarkspace-raw-data.png)

ERD를 작성하면 정규화하면 4개의 테이블로 분리됩니다. 추가로 데이터는 file정보를 읽어 DB에 넣기 때문에 파일의 정보를 저장합니다.
* 데이터 값 - market_space_analytics
* 상권의 그룹 정보 - market_space_group
* 상권의 기초 정보 - market_space_info
* 서비스 업종의 기초정보 - service_industry_info
* 원본 파일 중복 검사용 정보 - file_meta_info

*ERD*
![markspace-erd.png](document%2Fmarkspace-erd.png)


## Requirements
* Java 21
* OS(Mac, Linux, Windows) environment
* Docker
  * MySql 8.0.*

## Setup
### 1. Docker compose run & down
```sh
cd ~/<computer_home>/git/market-space
cd ops

docker-compose up -d

docker-compose down --volumn
```
### 2. Project build
```sh
./gradlew build
```
### 3. Create directory or file copy
```sh
mkdir /file_storage/csv
cp ./filestore/sample2023.csv /file_storage/csv
```

## Execution
### 기본 실행 
build 이후 sample 폴더 appbuild 폴더에 생성되어있음
```sh
java -jar appbuild/market-space-1.0.0.jar
```
### profile dev 변경 실행
```sh
java -jar appbuild/market-space-1.0.0.jar --spring.profiles.active=dev --app.market-space.file-store=/file_storage/csv
```

### swagger-ui
[Local Swagger link](http://localhost:8080/swagger-ui)

## Feature

* 로컬(서버)의 특정 디렉토리의 csv파일을 읽고 DB에 적재
* 기준년분기를 입력받아 해당 기준년분기에 존재하는 모든 서비스_업종명을 조회
* 기준년분기 와 상권코드를 입력받아 가장 많은 점포수를 가진 5가지 업종명을 조회
* 기준년분기 와 상권코드를 입력받아 상권별로 폐업률이 가장 낮은 업종명을 조회
* swagger 지원

## Constraints

* mac 환경에서 제공 데이터 지점의 csv 파일이 utf-8로 되어있지 않음.
  * 한글 깨짐 증상이 있어 ms-excel으로 utf-8 csv로 변환하여 filestore폴더에 넣음.
* arm cpu환경으로 인해 docker-compose mysql container 이미지를 x86 cpu로 변경해야할 수 있음.
  * docker-compose 파일내에서 image: amd64/mysql:8.0.31 값으로 변경
* 일부 결과를 위해 QueryDSL을 사용하지 않고 JDBC를 사용하여 조회기능 대체
  * inline subQuery 미지원과 RDB에서 지원하는 native함수를 이용하여 Query튜닝함.

## Change History
* MarketSpaceAnalytics 데이터 bulk insert시에 JPA에서 JDBC로 변경
  * 50만건 6분에서 30초로 개선
* 조회 API의 ORM Query개선을 위해 JDBC를 활용 native Query로 튜닝
  * ORM query 140ms에서 native Query 76ms 개선


