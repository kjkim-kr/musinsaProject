# 무신사 프로젝트

무신사는 다음 8개의 카테고리에서 상품을 하나씩 구매하여, 코디를 완성하는 서비스를 준비중입니다.
1. 상의
2. 아우터
3. 바지
4. 스니커즈
5. 가방
6. 모자
7. 양말
8. 액세서리

단, 구매 가격 외의 추가적인 비용(예, 배송비 등)은 고려하지 않고,<br>
브랜드의 카테고리에는 1개의 상품은 존재하고, 구매를 고려하는 모든 쇼핑몰에 상품 품절은 없다고 가정합니다.

1. 고객은 카테고리 별로 최저가격인 브랜드와 가격을 조회하고 총액이 얼마인지 확인할 수 있어야 합니다.
2. 고객은 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액이 얼마인지 확인할 수 있어야 합니다.
3. 고객은 특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 확인하고 각 브랜드 상품의 가격을 확인할 수 있어야 합니다.
4. 운영자는 새로운 브랜드를 등록하고, 모든 브랜드의 상품을 추가, 변경, 삭제할 수 있어야 합니다.

<br><br>
## 구현 범위에 대한 설명

### 주요 기능
- **Brand CRUD**: 브랜드를 생성, 조회, 수정 및 삭제하는 기능.
- **Category CRUD**: 카테고리를 생성, 조회, 삭제하는 기능.
- **Product CRUD**: 상품을 생성, 조회, 수정 및 삭제하는 기능.
- **최저가 조회**: 각 카테고리별로 최저가 상품 및 해당 브랜드, 총액을 조회하는 기능.
- **최저가 브랜드 조회**: 단일 브랜드로 모든 카테고리 상품 구매시, 최저가격의 브랜드의 각 카테고리별 상품 가격, 총액을 조회하는 기능.
- **특정 카테고리 상품 조회**: 특정 카테고리의 최저, 최고 가격 브랜드와 상품 가격을 조회하는 기능.

## 관리용 페이지
<a href=http://localhost:12012/swagger-ui/index.html>**Swagger API 목록**</a><br>
<a href=http://localhost:12012/manage/brand/>**Brand 관리 페이지**</a><br>
<a href=http://localhost:12012/manage/category/>**Category 관리 페이지**</a><br>
<a href=http://localhost:12012/manage/product/>**Product 관리 페이지**</a><br>
<a href=http://localhost:12012/manage/product/api>**Product API 관리 페이지**</a><br>

### API 목록
<br>

- **Brand API**
  - `POST /api/brands/add`: 새로운 브랜드 추가
  - `PUT /api/brands/update/{id}`: 브랜드 정보 업데이트
  - `DELETE /api/brands/delete`: 브랜드 삭제
  - `GET /api/brands/list/all`: 브랜드 전체 목록 조회
  - `POST /api/brands/list`: 브랜드 명칭으로 특정 브랜드 조회
- **Category API**
  - `POST /api/category/add`: 새로운 카테고리 추가
  - `DELETE /api/category/delete`: 카테고리 삭제
  - `GET /api/category/list/all`: 카테고리 전체 목록 조회
  - `POST /api/category/list`: 카테고리 명칭으로 특정 카테고리 조회
- **Product API**
  - `POST /api/product/add`: 새로운 상품 추가
  - `PUT /api/product/update/{id}`: 상품 정보 업데이트
  - `DELETE /api/product/delete`: 상품 삭제
  - `DELETE /api/product/delete/{id}`: 상품 id로 상품 삭제
  - `GET /api/product/list/all`: 상품 전체 목록 조회
  - `POST /api/product/list`: 상품 명칭으로 특정 상품 조회
  - `GET /api/product/list/category/{name}`: 특정 카테고리의 최저, 최고 가격 상품 조회
  - `GET /api/product/list/min_price_brand`: 단일 브랜드로 모든 카테고리 상품 구매 시, 최저 가격 브랜드 정보 조회
  - `GET /api/product/list/min_price_category`: 각 카테고리별로 최저가 상품 및 해당 브랜드, 총액 조회



<br><br>
## Error Code 목록

### 일반 오류 코드
| 코드 | 이름                  | 설명                     |
|------|-----------------------|--------------------------|
| 10   | UNEXPECTED_ATTRIBUTE  | 예기치 않은 속성 오류     |
| 11   | INVALID_ARGUMENT      | 잘못된 인수 오류         |
| 12   | DELETE_FAILED         | 삭제 실패                |
| 13   | DATA_NOT_FOUND        | 데이터 찾기 실패         |

### 데이터베이스 관련 오류 코드
| 코드 | 이름                  | 설명                     |
|------|-----------------------|--------------------------|
| 100  | FOREIGN_KEY_VIOLATION | 외래 키 제약 조건 위반    |
| 101  | UNIQUE_KEY_VIOLATION  | 고유 키 제약 조건 위반    |

### 서버 오류 코드
| 코드 | 이름                  | 설명                     |
|------|-----------------------|--------------------------|
| 500  | INTERNAL_SERVER_ERROR | 내부 서버 오류           |

### SQL 오류 코드
| 코드 | 이름                               | 설명                           |
|------|------------------------------------|--------------------------------|
| 1062 | MYSQL_PK_UNIQUE_VIOLATION          | MySQL 기본 키 고유 제약 조건 위반 |
| 23505| POSTGRESQL_H2_PK_UNIQUE_VIOLATION  | PostgreSQL/H2 기본 키 고유 제약 조건 위반 |
| 2627 | SQLSERVER_PK_UNIQUE_VIOLATION      | SQL Server 기본 키 고유 제약 조건 위반 |
| 1    | ORACLE_PK_UNIQUE_VIOLATION         | Oracle 기본 키 고유 제약 조건 위반 |
| 1452 | MYSQL_FOREIGN_KEY_VIOLATION        | MySQL 외래 키 제약 조건 위반    |
| 23503| POSTGRESQL_FOREIGN_KEY_VIOLATION   | PostgreSQL 외래 키 제약 조건 위반 |
| 23506| H2_FOREIGN_KEY_VIOLATION           | H2 외래 키 제약 조건 위반       |
| 547  | SQLSERVER_FOREIGN_KEY_VIOLATION    | SQL Server 외래 키 제약 조건 위반 |
| 2291 | ORACLE_FOREIGN_KEY_VIOLATION       | Oracle 외래 키 제약 조건 위반    |


<br><br>
# API 입출력 예시

## 최저가 조회
각 카테고리별로 최저가 상품 및 해당 브랜드, 총액을 조회하는 기능.

### 요청 경로
GET `http://localhost:12012/api/product/list/min_price_category`

### 입력
없음

### 출력
```json
{
    "목록": [
        {
            "카테고리": "가방",
            "브랜드": "A",
            "가격": "2,000"
        },
        {
            "카테고리": "바지",
            "브랜드": "D",
            "가격": "3,000"
        },
        {
            "카테고리": "상의",
            "브랜드": "C",
            "가격": "10,000"
        },
        {
            "카테고리": "양말",
            "브랜드": "I",
            "가격": "1,700"
        },
        {
            "카테고리": "액세서리",
            "브랜드": "F",
            "가격": "1,900"
        },
        {
            "카테고리": "아우터",
            "브랜드": "E",
            "가격": "5,000"
        },
        {
            "카테고리": "모자",
            "브랜드": "D",
            "가격": "1,500"
        },
        {
            "카테고리": "스니커즈",
            "브랜드": "G",
            "가격": "9,000"
        }
    ],
    "총액": "34,100"
}
```


## 최저가 브랜드 조회 
단일 브랜드로 모든 카테고리 상품 구매시, 최저가격의 브랜드의 각 카테고리별 상품 가격, 총액을 조회하는 기능.

### 요청 경로
GET `http://localhost:12012/api/product/list/min_price_brand`

### 입력
없음

### 출력
```json
{
    "최저가": {
        "브랜드": "D",
        "카테고리": [
            {
                "카테고리": "상의",
                "가격": "10,100"
            },
            {
                "카테고리": "아우터",
                "가격": "5,100"
            },
            {
                "카테고리": "바지",
                "가격": "3,000"
            },
            {
                "카테고리": "스니커즈",
                "가격": "9,500"
            },
            {
                "카테고리": "가방",
                "가격": "2,500"
            },
            {
                "카테고리": "모자",
                "가격": "1,500"
            },
            {
                "카테고리": "양말",
                "가격": "2,400"
            },
            {
                "카테고리": "액세서리",
                "가격": "2,000"
            }
        ],
        "총액": "36,100"
    }
}
```

## 특정 카테고리 상품 조회
특정 카테고리의 최저, 최고 가격 브랜드와 상품 가격을 조회하는 기능.

### 요청 경로
GET `http://localhost:12012/api/product/list/category/{name}`

### Parameter
name(category 명) : 상의, 바지 등

### 출력 예시
```json
{
    "카테고리": "바지",
    "최저가": [
        {
            "브랜드명": "D",
            "가격": "3,000"
        }
    ],
    "최고가": [
        {
            "브랜드명": "A",
            "가격": "4,200"
        }
    ]
}
```

### 요청 실패
category 명이 없을 경우 -> 상하의 등

```json
{"errorCode":13, "errorMessage":"data not found"}
```

<br>

## 브랜드 데이터 입력
특정 브랜드 데이터를 입력하는 기능

### 요청 경로
POST `http://localhost:12012/api/brand/add`

### 입력
Request Body (Application/json) 형식

```json
{"name": "새브랜드명"}
```

### 출력 예시
```json
{
    "status": 200,
    "message": "success",
    "data": {
        "id": 73,
        "name": "새브랜드명",
        "addDate": "2024-06-17 21:51:28"
    }
}
```

### 요청 실패
request body json에 name 필드가 없는 경우
```json
{"errorCode":10, "errorMessage":"invalid attribute"}
```
입력하려는 브랜드 이름이 중복되는 경우
```json
{"errorCode":101, "errorMessage":"unique key violation"}
```


## 브랜드 데이터 삭제
특정 브랜드 데이터를 삭제하는 기능

### 요청 경로
DELETE `http://localhost:12012/api/brand/delete`

### 입력
Request Body (Application/json) 형식

```json
{"name": "삭제할브랜드명"}
```

### 출력 예시
```json
{"status":200, "message":"success"}
```

### 요청 실패
request body json에 name 필드가 없는 경우
```json
{"errorCode":10, "errorMessage":"invalid attribute"}
```
지정한 브랜드 명이 없는 경우
```json
{"errorCode":12, "errorMessage":"fail to delete data"}
```


## 브랜드 데이터 업데이트
특정 브랜드 데이터를 업데이트 하는 기능

### 요청 경로
PUT `http://localhost:12012/api/brand/update/{id}`

### Parameter
id : 업데이트 할 브랜드 id

### 입력
Request Body (Application/json) 형식

```json
{"name": "업데이트할브랜드명"}
```

### 출력 예시
```json
{
    "status": 200,
    "message": "success",
    "data": {
        "id": 76,
        "name": "새로운이름",
        "addDate": "2024-06-17 22:01:04"
    }
}
```

### 요청 실패
지정한 브랜드 id가 없는 경우
```json
{"errorCode":13, "errorMessage":"data not found"}
```
변경하려는 이름이 기존 데이터와 중복되는 경우
```json
{"errorCode":101, "errorMessage":"unique key violation"}
```


## 브랜드 데이터 검색
특정한 브랜드 명을 검색하는 기능

### 요청 경로
POST `http://localhost:12012/api/brand/list`

### 입력
Request Body (Application/json) 형식

```json
{"name": "새로운이름"}
```

### 출력 예시
```json
{
    "status": 200,
    "message": "success",
    "data": {
        "id": 76,
        "name": "새로운이름",
        "addDate": "2024-06-17 22:01:04"
    }
}
```

### 요청 실패
지정한 브랜드 이름이 없는 경우
```json
{"errorCode":13, "errorMessage":"data not found"}
```


## 브랜드 데이터 전체 검색
브랜드 데이터 전체 목록을 검색하는 기능

### 요청 경로
GET `http://localhost:12012/api/brand/list/all`

### 입력
없음

### 출력 예시
```json
{
    "status": 200,
    "message": "success",
    "data": [
        {
            "id": 76,
            "name": "새로운이름",
            "addDate": "2024-06-17 22:01:04"
        },
        {
            "id": 9,
            "name": "I",
            "addDate": "2024-06-15 20:25:18"
        },
        {
            "id": 8,
            "name": "H",
            "addDate": "2024-06-15 20:25:18"
        },
        {
            "id": 7,
            "name": "G",
            "addDate": "2024-06-15 20:25:18"
        },
        {
            "id": 6,
            "name": "F",
            "addDate": "2024-06-15 20:25:18"
        },
        {
            "id": 5,
            "name": "E",
            "addDate": "2024-06-15 20:25:18"
        },
        {
            "id": 4,
            "name": "D",
            "addDate": "2024-06-15 20:25:18"
        },
        {
            "id": 3,
            "name": "C",
            "addDate": "2024-06-15 20:25:18"
        },
        {
            "id": 2,
            "name": "B",
            "addDate": "2024-06-15 20:25:18"
        },
        {
            "id": 1,
            "name": "A",
            "addDate": "2024-06-15 20:25:18"
        }
    ]
}
```


<br>
## 카테고리 데이터 입력
특정 카테고리 데이터를 입력하는 기능

### 요청 경로
POST `http://localhost:12012/api/category/add`

### 입력
Request Body (Application/json) 형식

```json
{"name": "새카테고리명"}
```

### 출력 예시
```json
{
    "status": 200,
    "message": "success",
    "data": {
        "id": 65,
        "name": "새카테고리명",
        "addDate": "2024-06-17 21:51:28"
    }
}
```

### 요청 실패
request body json에 name 필드가 없는 경우
```json
{"errorCode":10, "errorMessage":"invalid attribute"}
```
입력하려는 카테고리 이름이 중복되는 경우
```json
{"errorCode":101, "errorMessage":"unique key violation"}
```


## 카테고리 데이터 삭제
특정 카테고리 데이터를 삭제하는 기능

### 요청 경로
DELETE `http://localhost:12012/api/category/delete`

### 입력
Request Body (Application/json) 형식

```json
{"name": "삭제할카테고리명"}
```

### 출력 예시
```json
{"status":200, "message":"success"}
```

### 요청 실패
request body json에 name 필드가 없는 경우
```json
{"errorCode":10, "errorMessage":"invalid attribute"}
```
지정한 카테고리 명이 없는 경우
```json
{"errorCode":12, "errorMessage":"fail to delete data"}
```

## 카테고리 데이터 검색
특정한 카테고리 명을 검색하는 기능

### 요청 경로
POST `http://localhost:12012/api/category/list`

### 입력
Request Body (Application/json) 형식

```json
{"name": "새카테고리명"}
```

### 출력 예시
```json
{
    "status": 200,
    "message": "success",
    "data": {
        "id": 65,
        "name": "새카테고리명",
        "addDate": "2024-06-17 22:01:04"
    }
}
```

### 요청 실패
지정한 카테고리명이 없는 경우
```json
{"errorCode":13, "errorMessage":"data not found"}
```


## 카테고리 데이터 전체 검색
카테고리 데이터 전체 목록을 검색하는 기능

### 요청 경로
GET `http://localhost:12012/api/brand/category/all`

### 입력
없음

### 출력 예시
```json
{
    "status": 200,
    "message": "success",
    "data": [
        {
            "id": 65,
            "name": "새카테고리",
            "addDate": "2024-06-17 22:45:05"
        },
        {
            "id": 8,
            "name": "액세서리",
            "addDate": "2024-06-15 20:25:18"
        },
        {
            "id": 7,
            "name": "양말",
            "addDate": "2024-06-15 20:25:18"
        },
        {
            "id": 6,
            "name": "모자",
            "addDate": "2024-06-15 20:25:18"
        },
        {
            "id": 5,
            "name": "가방",
            "addDate": "2024-06-15 20:25:18"
        },
        {
            "id": 4,
            "name": "스니커즈",
            "addDate": "2024-06-15 20:25:18"
        },
        {
            "id": 3,
            "name": "바지",
            "addDate": "2024-06-15 20:25:18"
        },
        {
            "id": 2,
            "name": "아우터",
            "addDate": "2024-06-15 20:25:18"
        },
        {
            "id": 1,
            "name": "상의",
            "addDate": "2024-06-15 20:25:18"
        }
    ]
}
```


<br>
## 상품 데이터 입력
특정 상품 데이터를 입력하는 기능

### 요청 경로
POST `http://localhost:12012/api/product/add`

### 입력
Request Body (Application/json) 형식
brand, category id는 존재해야 하는 값

```json
{
  "brand": {
    "id": 1
  },
  "category": {
    "id": 1
  },
  "name": "새로운상품",
  "price": 12345
}
```

### 출력 예시
```json
{
    "status": 200,
    "message": "success",
    "data": {
        "id": 73,
        "brand": {
            "id": 1
        },
        "category": {
            "id": 1
        },
        "name": "새로운상품",
        "price": 12345,
        "addDate": "2024-06-17 22:55:12"
    }
}
```

### 요청 실패
request body json에 name 필드가 없는 경우
```json
{"errorCode":10, "errorMessage":"invalid attribute"}
```
brand, category id 값이 없는 경우
```json
{"errorCode":100, "errorMessage":"foreign key violation"}
```

## 상품 데이터 id로 삭제
특정 상품 데이터를 상품의 id로 삭제하는 기능

### 요청 경로
DELETE `http://localhost:12012/api/product/delete/{id}`

### Parameter
id : 삭제할 상품 id

### 출력 예시
```json
{"status":200, "message":"success"}
```

### 요청 실패
지정한 상품 id가 없는 경우
```json
{"errorCode":12, "errorMessage":"fail to delete data"}
```



## 상품 데이터 삭제
특정 상품 데이터를 삭제하는 기능

### 요청 경로
DELETE `http://localhost:12012/api/product/delete`

### 입력
Request Body (Application/json) 형식
```json
{
  "brand": {
    "id": 1
  },
  "category": {
    "id": 1
  },
  "name": "새로운상품"
}
```

### 출력 예시
```json
{"status":200, "message":"success"}
```

### 요청 실패
request body json에 name, brand, category 필드 등이 없는 경우
```json
{"errorCode":10, "errorMessage":"invalid attribute"}
```
지정한 상품 정보 (brand_id, category_id, name)에 대응되는 데이터가 없는 경우
```json
{"errorCode":12, "errorMessage":"fail to delete data"}
```

## 상품 데이터 업데이트
특정 상품의 이름, 가격을 수정하는 기능

### 요청 경로
POST `http://localhost:12012/api/product/update/{id}`

### Parameter
id : 업데이트할 상품 id

### 입력
Request Body (Application/json) 형식

둘 중 한 값만 업데이트 할 경우
```json
{"name": "새상품이름"}
```
```json
{"price": 11111}
```

둘 다 업데이트 할 경우
```json
{"name": "새상품이름", "price": 11111}
```

### 출력 예시
```json
{
    "status": 200,
    "message": "success",
    "data": {
        "id": 73,
        "brand": {
            "id": 1,
            "name": "A",
            "addDate": "2024-06-15 20:25:18"
        },
        "category": {
            "id": 1,
            "name": "상의",
            "addDate": "2024-06-15 20:25:18"
        },
        "name": "새상품이름",
        "price": 11111,
        "addDate": "2024-06-17 22:55:12"
    }
}
```

### 요청 실패
request body json에 name, price 필드가 둘 다 없는 경우
```json
{"errorCode":10, "errorMessage":"invalid attribute"}
```
바꾸려는 상품의 id가 없는 경우
```json
{"errorCode":13,"errorMessage":"data not found"}
```
바꾸려는 상품 정보 (brand_id, category_id, name)의 데이터가 이미 있는 경우
```json
{"errorCode":101, "errorMessage":"unique key violation"}
```


## 상품 데이터 검색
특정한 상품 데이터를 검색하는 기능

### 요청 경로
POST `http://localhost:12012/api/product/list`

### 입력
Request Body (Application/json) 형식

```json
{
  "brand": {
    "id": 1
  },
  "category": {
    "id": 1
  },
  "name": "새상품이름"
}
```

### 출력 예시
```json
{
    "status": 200,
    "message": "success",
    "data": {
        "id": 73,
        "brand": {
            "id": 1,
            "name": "A",
            "addDate": "2024-06-15 20:25:18"
        },
        "category": {
            "id": 1,
            "name": "상의",
            "addDate": "2024-06-15 20:25:18"
        },
        "name": "새상품이름",
        "price": 11111,
        "addDate": "2024-06-17 22:55:12"
    }
}
```

### 요청 실패
request body json에 name 필드가 없는 경우
```json
{"errorCode":10, "errorMessage":"invalid attribute"}
```
지정한 (브랜드, 카테고리, 이름)의 상품 데이터가 없는 경우
```json
{"errorCode":13, "errorMessage":"data not found"}
```


## 상품 데이터 전체 검색
상품 데이터 전체 목록을 검색하는 기능

### 요청 경로
GET `http://localhost:12012/api/product/list/all`

### 입력
없음

### 출력 예시
```json
{
    "status": 200,
    "message": "success",
    "data": [
        {
            "id": 73,
            "brand": {
                "id": 1,
                "name": "A",
                "addDate": "2024-06-15 20:25:18"
            },
            "category": {
                "id": 1,
                "name": "상의",
                "addDate": "2024-06-15 20:25:18"
            },
            "name": "새상품이름",
            "price": 11111,
            "addDate": "2024-06-17 22:55:12"
        },
        {
            "id": 1,
            "brand": {
                "id": 1,
                "name": "A",
                "addDate": "2024-06-15 20:25:18"
            },
            "category": {
                "id": 1,
                "name": "상의",
                "addDate": "2024-06-15 20:25:18"
            },
            "name": "상의A",
            "price": 11200,
            "addDate": "2024-06-15 20:34:34"
        }
    ]
}
```

<br><br>
## 코드 빌드, 테스트, 실행 방법

### 사전 요구사항
- Java 17 이상
- Gradle
- H2 데이터베이스
- Git

### 빌드 및 실행
1. **프로젝트 클론**
    ```bash
    git clone https://github.com/kjkim-kr/musinsaProject.git
    cd musinsaProject
    ```

2. **빌드**
    ```bash
    ./gradlew build
    ```

3. **테스트**
    ```bash
    ./gradlew clean test
    ```

4. **애플리케이션 실행**
    ```bash
    java -jar build/libs/musinsaProject-0.0.1-SNAPSHOT.jar
    ```
