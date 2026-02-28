# erum

한국어 이름 생성 라이브러리입니다. 현재 구현 기준으로 `next`, `next(lastName)`, `unique`, `batch` API를 제공합니다.

## 1. 기능

- 한국어 이름 생성
- 성 포함/미포함 생성 (`koreanOptions.lastName`)
- 고정 성으로 생성 (`next("김")`)
- 중복 없는 이름 생성 (`unique`, `batch(..., allowDuplicate = false)`)
- 시드 기반 재현 가능한 결과 (`seed`)
- 블록리스트 필터링 (`filtersEnabled`, `blocklist`)
- 이름 길이 상한 제어 (`koreanOptions.maxFirstNameLength`, 1~3)

## 2. 변수/config

`NameGenerator`는 `Config`를 받습니다.

```kotlin
data class Config(
    val seed: Long? = null,
    val maxAttemptsPerName: Int = 100,
    val filtersEnabled: Boolean = false,
    val blocklist: Set<String> = emptySet(),
    val koreanOptions: KoreanNameOptions = KoreanNameOptions(),
)
```

`KoreanNameOptions`는 한국어 이름 생성 전용 옵션입니다.

```kotlin
data class KoreanNameOptions(
    val lastName: Boolean = false,
    val maxFirstNameLength: Int = 2,
)
```

- `lastName`: `true`면 성+이름, `false`면 이름만 생성
- `maxFirstNameLength`: 이름 길이 상한 (1~3)
- `maxAttemptsPerName`: 조건(필터/유니크)을 만족하는 이름을 찾기 위한 최대 시도 횟수
- `filtersEnabled`: `true`일 때 `blocklist` 필터 활성화
- `blocklist`: 포함되면 제외할 문자열 목록
- `seed`: 동일값일 때 동일 시퀀스 생성

## 3. usage

전체 예제는 [erum-sample](./erum-sample) 모듈을 참고하세요.

### Gradle (멀티모듈 프로젝트 내부 사용)

```kotlin
dependencies {
    implementation(project(":erum-core"))
}
```

### 기본 생성

```kotlin
import set.Config
import set.KoreanNameOptions
import set.NameGenerator

val generator = NameGenerator(
    Config(
        seed = 42L,
        koreanOptions = KoreanNameOptions(
            lastName = true,
            maxFirstNameLength = 2,
        )
    )
)

val a = generator.next()        // 예: 김하민
val b = generator.unique()      // 중복 없는 이름
val c = generator.batch(10, allowDuplicate = false)
```

### 고정 성으로 생성

```kotlin
val generator = NameGenerator(Config())
val name = generator.next("김") // 항상 김으로 시작
```

### 필터 사용

```kotlin
val generator = NameGenerator(
    Config(
        filtersEnabled = true,
        blocklist = setOf("하", "민"),
        koreanOptions = KoreanNameOptions(lastName = false, maxFirstNameLength = 2)
    )
)

val name = generator.next()
```

### 동시성 주의사항

`NameGenerator`는 내부적으로 `UniqueTracker`를 사용하며, thread-safe 하지 않습니다.
하나의 `NameGenerator` 인스턴스를 여러 스레드에서 동시에 공유하는 사용은 권장하지 않습니다.

## 4. MIT license

이 프로젝트는 MIT License를 따릅니다. 자세한 내용은 [LICENSE](./LICENSE)를 참고하세요.
