# 무신사 프로젝트

## 구현 범위에 대한 설명

### 주요 기능
- **Brand CRUD**: 브랜드를 생성, 조회, 수정 및 삭제하는 기능.
- **Category CRUD**: 카테고리를 생성, 조회, 삭제하는 기능.
- **Product CRUD**: 상품을 생성, 조회, 수정 및 삭제하는 기능.
- **최저가 조회**: 각 카테고리별로 최저가 상품 및 해당 브랜드, 총액을 조회하는 기능.
- **최저가 브랜드 조회**: 단일 브랜드로 모든 카테고리 상품 구매시, 최저가격의 브랜드의 각 카테고리별 상품 가격, 총액을 조회하는 기능.
- **특정 브랜드 상품 조회**: 특정 카테고리의 최저, 최고 가격 브랜드와 상품 가격을 조회하는 기능.


### API 목록
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



# JSON 입출력 예시

## JSON 입력

다음은 제품 카테고리에 대한 JSON 입력 예시입니다:

```json
{
  "category": "laptops",
  "products": [
    {"brand": "Dell", "price": 800},
    {"brand": "HP", "price": 700},
    {"brand": "Lenovo", "price": 600}
  ]
}
```




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
    
    ```

3. **테스트**
    ```bash
    
    ```

4. **애플리케이션 실행**
    ```bash
    
    ```
