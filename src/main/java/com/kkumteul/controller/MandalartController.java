package com.kkumteul.controller;
import com.kkumteul.domain.Category;
import com.kkumteul.domain.Mandalart;
import com.kkumteul.domain.User;
import com.kkumteul.repository.UserRepository;
import com.kkumteul.service.MandalartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // [필수 추가]
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import com.kkumteul.domain.Cell;
import java.util.Comparator;

import java.util.Map; // [필수 추가]


@Controller
@RequiredArgsConstructor
public class MandalartController {

    private final MandalartService mandalartService;
    private final UserRepository userRepository;

    /**
     * 1. 만다라트 입력 폼 화면 띄우기
     */
    @GetMapping("/mandalart/new")
    public String createForm(Model model, @AuthenticationPrincipal OAuth2User principal) {
        // ... 기존 코드 ...
        model.addAttribute("isEdit", false); // 확실하게 false를 넣어줌
        model.addAttribute("goals", new HashMap<String, String>());
        return "createForm";
    }

    /**
     * 2. 만다라트 저장 (81개 파라미터 수집 방식)
     * 우리가 만든 createMandalartFromMap을 사용하는 핵심 로직이야!
     */
    @PostMapping("/mandalart/save")
    public String save(@RequestParam Map<String, String> allParams,
                       @RequestParam(value = "mandalartId", required = false) Long mandalartId, // id 추가 수집
                       @AuthenticationPrincipal OAuth2User oAuth2User,
                       RedirectAttributes redirectAttributes) {

        String email = oAuth2User.getAttribute("email");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("로그인 정보를 찾을 수 없습니다."));

        Long savedId;

        // 1. mandalartId가 넘어왔다면 업데이트 수행
        if (mandalartId != null) {
            savedId = mandalartService.updateMandalartFromMap(mandalartId, allParams);
        } else {
            // 2. 없다면 신규 생성
            savedId = mandalartService.createMandalartFromMap(allParams, user.getId());
        }

        redirectAttributes.addAttribute("id", savedId);
        return "redirect:/mandalart/main/{id}";
    }

    /**
     * 3. 메인 결과 화면 (main.html) 보여주기
     */
    @GetMapping("/mandalart/main/{id}") // user id 번호 인식하여 입장
    public String main(@PathVariable("id") Long id, @AuthenticationPrincipal OAuth2User principal, Model model) { // {id}를 자바 변수 'id'로 변환

        // 세션에서 구글 사용자 이름 꺼내기
        if (principal != null) {
            String name = principal.getAttribute("name");
            model.addAttribute("userName", name);
        }

        Mandalart mandalart = mandalartService.findMandalart(id);
        // 서비스에게 DB에서 해당 ID의 만다라트 덩어리를 통째로 가져오라는 메소드 실행

        // 1. D-Day 계산 (2026년 12월 31일 기준)
        LocalDate today = LocalDate.now(); // 오늘 날짜 확인
        LocalDate endOfYear = LocalDate.of(2026, 12, 31);
        //목표 마감일 설정
        long dDay = java.time.temporal.ChronoUnit.DAYS.between(today, endOfYear);
        // 두 날짜 사이의 일수 차이 계산

        // 2. 전체 달성률 계산
        // 모든 카테고리의 모든 세부 목표를 합쳐서 계산해!
        List<Cell> allcells = mandalart.getCategories().stream()
                // stream으로 카테고리들을 한줄로 나열
                .flatMap(category -> category.getCells().stream())
                //세부목표들도 다 꺼냄
                .toList(); // 전체를 리스트로 묶음

        long totalCount = allcells.size(); // 전체 목표 수 계산
        long completedCount = allcells.stream().filter(Cell::getIsCompleted).count();
        // 완료 상태만 골라서 세기
        int progressPercent = totalCount == 0 ? 0 : (int) ((double) completedCount / totalCount * 100);
        // 0으로 나누는 에러를 방지하면서 퍼센트 계산

        // 3. 최근 활동 (완료된 목표 중 최신 3개만 추출)
        List<Cell> recentActivities = allcells.stream()
                .filter(Cell::getIsCompleted) // 완료된 것만 필터
                .sorted(Comparator.comparing(Cell::getCreatedAt).reversed())
                // 생성일 기준으로 내림차순 정렬
                .limit(3) // 3개 뽑기
                .toList();

        // 모델에 담아서 메인으로 이동
        model.addAttribute("mandalart", mandalart);
        model.addAttribute("dDay", dDay);
        model.addAttribute("progress", progressPercent);
        model.addAttribute("recentActivities", recentActivities);

        return "main";
    }

    @GetMapping("/mandalart/category/{id}")
    public String categoryDetailPage(@PathVariable("id") Long id, Model model) {
        // JS에서 쓸 수 있게 ID만 넘겨주면 돼!
        model.addAttribute("categoryId", id);

        // 만약 상단 헤더에 이름을 띄우고 싶다면 여기서 유저 정보를 추가해도 좋아.
        // model.addAttribute("userName", ...);



        return "Detail"; // templates/categoryDetail.html을 찾아가게 함
    }

    // 수정 시
    @GetMapping("/mandalart/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal OAuth2User principal) {
        // ... 기존 코드 ...
        String name = "방문자"; // 기본값
        if (principal != null) {
            // OAuth2 공급자(Google, Kakao 등)에 따라 키값이 다를 수 있음
            name = principal.getAttribute("name");
        }

        // 2. 모델에 "userName"이라는 이름으로 전달
        model.addAttribute("userName", name);

        model.addAttribute("isEdit", true); // 확실하게 true를 넣어줌
        model.addAttribute("goals", mandalartService.getCellMap(id));
        return "createForm";
    }




}