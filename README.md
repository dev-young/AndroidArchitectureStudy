# AndroidArchitectureStudy

### Clean Architecture study with GitHub API

### Stack

- MVVM
- Hilt
- Coroutine

이벤트 버스를 적용하는 방식을 살짝 다르게 해보았다. 이런 방식이 과연 올바른 방식인지는 확인이 필요하다.
(SharedFlow<Event> 를 Usecase를 통해 전달하는 방식에서 직접 뷰모델에 주입하여 사용하는 방식으로 바꿈)
