# 🎯 만다르트 계획표 관리 플랫폼 (IDEAL-Kkumteul)

사용자가 핵심 목표를 설정하고 이를 달성하기 위한 세부 계획을 시각적으로 관리할 수 있는 **'꿈을 담는 틀, 꿈틀' 서비스** 구현 프로젝트입니다. <br>
Spring Boot를 기반으로 한 풀스택 웹 아키텍처로 구축되었으며, 직관적인 UI를 통해 만다르트 기법을 활용한 목표 관리를 지원합니다.
<br>
<br>

## 👥 프로젝트 정보
- **개발자:** 이세연, 이나연, 조혜인
<br>

## 📂 폴더 구조 (Project Structure)
```text
IDEAL-Kkumteul/
├── build.gradle
├── src/
│   ├── main/
│   │   ├── java/com/kkumteul/
│   │   │   ├── KkumTeulApplication.java
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── MandalartController.java
│   │   │   │   └── MandalartApiController.java
│   │   │   ├── domain/
│   │   │   │   ├── User.java
│   │   │   │   ├── Mandalart.java
│   │   │   │   ├── Category.java
│   │   │   │   └── Cell.java
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── MandalartRepository.java
│   │   │   │   ├── CategoryRepository.java
│   │   │   │   └── CellRepository.java
│   │   │   └── service/
│   │   │       ├── MandalartService.java
│   │   │       └── CustomOAuth2UserService.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   ├── js/
│   │       │   └── images/
│   │       ├── templates/
│   │       └── application.properties
│   └── test/
├── .gitignore
└── README.md
```
<br>


## 1. 개발 목표 및 특징
- **통합형 풀스택 아키텍처:** Spring Boot 프레임워크 내에서 백엔드 로직과 프론트엔드 리소스를 통합 관리하여 개발 효율성을 극대화한 구조 설계.
- **동적 만다르트 구현:** 사용자 입력에 따라 3x3 중심 구조에서 확장되는 만다르트 계획표를 JavaScript를 활용해 동적으로 렌더링.
- **데이터 영속성 및 ORM 적용:** Spring Data JPA를 활용해 목표 데이터를 객체 단위로 관리하고 MySQL 데이터베이스와 안정적인 연동 구현.
- **시각적 목표 관리:** 도서, 경제, 운동 등 카테고리별 맞춤형 이미지를 활용하여 사용자가 목표를 직관적으로 인지할 수 있도록 설계.
<br>
<br>

## 2. 주요 기술 스택 및 구성

### 🛠 서버 및 비즈니스 로직 (Back-end)
| 분류 | 기술 스택 | 주요 역할 |
| :--- | :--- | :--- |
| **Tool** | IntelliJ IDEA | 통합 개발 환경 및 서버 제어 |
| **Framework** | Spring Boot 3.x | 서버 사이드 로직 처리 및 웹 서비스 구동 |
| **Language** | Java 17 | 비즈니스 로직 및 객체 지향 프로그래밍 구현 |
| **Database** | MySQL | 사용자 정보 및 만다르트 계획 데이터 저장 |
| **ORM** | Spring Data JPA | 데이터베이스 객체 매핑 및 효율적인 데이터 처리 |
| **Security** | Google OAuth 2.0 | 소셜 로그인을 통한 간편한 사용자 인증 및 보안 관리 |

### 💻 화면 및 사용자 인터페이스 (Front-end)
| 분류 | 기술 스택 | 주요 역할 |
| :--- | :--- | :--- |
| **View Engine** | Thymeleaf / HTML5 | 서버 데이터를 활용한 동적 웹 페이지 생성 |
| **Styling** | CSS3 | 프로젝트 테마 및 반응형 레이아웃 설계 |
| **Script** | JavaScript (ES6+) | 화면 동적 제어 및 사용자 인터랙션 처리 |
| **Build Tool** | Gradle | 프로젝트 빌드 및 의존성 라이브러리 관리 |
<br>

## 3. 주요 기능 및 서비스 구조
- **만다르트 계획 관리:** 핵심 목표를 중심으로 8개의 세부 목표와 64개의 실천 과제를 계층 구조로 등록하고 관리.
- **이미지 연동 시스템:** 목표 카테고리에 맞는 이미지를 매칭하여 시각적인 목표 성취욕구 자극.
- **소셜 로그인 연동:** Google OAuth를 활용해 별도의 회원가입 없이 개인별 만다르트 저장소 제공.
- **데이터 실시간 연동:** 사용자가 입력하거나 수정한 목표치를 즉각적으로 서버 DB에 저장하여 데이터 유실 방지.
<br>
<br>
