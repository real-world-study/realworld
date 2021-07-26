## 개발환경 구축

1. 메인 프로젝트를 원하는 위치에 먼저 clone 받은 후 clone한 폴더로 이동합니다.

```
$ git clone -b main --single-branch https://github.com/real-world-study/realworld
$ cd realworld
```

2. 본인의 아이디로 branch를 생성한 후 push한 후 working branch를 생성합니다.

```
$ git checkout -b {본인_아이디}
$ git push origin {본인_아이디}
$ git checkout -b {본인_아이디}_working
ex) git checkout -b jinyoungchoi95
ex) git push origin jinyoungchoi95
ex) git checkout -b jinyoungchoi95_working
```

3. IDE로 해당 프로젝트를 열고 작업합니다.

4. 기능 구현 후 add, commit 합니다. (커밋의 방법은 자유롭게 진행하시면 됩니다.)

```
$ git status	// 변경된 파일 확인
$ git add -A(또는 .)	// 변경된 전체 파일을 한번에 반영
$ git commit -m "메시지"	// 작업한 내용을 메시지에 기록
```

5. 저장소(working repository) 에 push 합니다

```
$ git push origin {본인_아이디}_working
ex) git push origin jinyoungchoi95_working
```

6. 코드 리뷰 후 merge되었다면 3-5를 다시 진행합니다.