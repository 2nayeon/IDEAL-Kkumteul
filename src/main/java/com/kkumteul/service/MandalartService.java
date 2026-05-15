package com.kkumteul.service;

import com.kkumteul.domain.User;
import com.kkumteul.domain.Category;
import com.kkumteul.domain.Mandalart;
import com.kkumteul.domain.Cell;
import com.kkumteul.repository.UserRepository;
import com.kkumteul.repository.CategoryRepository;
import com.kkumteul.repository.MandalartRepository;
import com.kkumteul.repository.CellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MandalartService {

    private final MandalartRepository mandalartRepository;
    private final CategoryRepository categoryRepository;
    private final CellRepository cellRepository;
    private final UserRepository userRepository;

    /**
     * 1. 만다라트 신규 저장
     */
    @Transactional
    public Long createMandalartFromMap(Map<String, String> goalParams, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));

        // 중앙 핵심 목표 타이틀 추출 (가장 중앙 인덱스 40)
        String mainGoalTitle = goalParams.getOrDefault("goal[4][4]", "2026 만다라트");
        Mandalart mandalart = new Mandalart(user, mainGoalTitle);

        // 9개의 카테고리(블록) 순회
        for (int i = 0; i < 9; i++) {
            if (i == 4) continue; // 중앙 블록은 카테고리 리스트에 포함하지 않음 (UI 정책에 따라)

            // 각 블록의 중앙값(j=4)을 카테고리 이름으로 사용
            String categoryName = goalParams.get("goal[" + i + "][4]");
            Category category = new Category(mandalart, i, categoryName);

            // 해당 블록 내 나머지 8개 칸(세부 목표) 순회
            for (int j = 0; j < 9; j++) {
                if (j == 4) continue;

                String cellContent = goalParams.get("goal[" + i + "][" + j + "]");
                if (cellContent != null && !cellContent.isEmpty()) {
                    new Cell(category, j, cellContent);
                }
            }
        }
        return mandalartRepository.save(mandalart).getId();
    }

    /**
     * 2. 만다라트 수정 (핵심: Dirty Checking 활용)
     */
    @Transactional
    public Long updateMandalartFromMap(Long mandalartId, Map<String, String> goalParams) {
        Mandalart mandalart = mandalartRepository.findById(mandalartId)
                .orElseThrow(() -> new IllegalArgumentException("수정할 만다라트가 없습니다."));

        // 기존 카테고리들을 순회하며 데이터 업데이트
        for (Category category : mandalart.getCategories()) {
            int i = category.getPosition(); // DB에 저장된 블록 위치 (0~8)

            for (Cell cell : category.getCells()) {
                int j = cell.getIndex(); // DB에 저장된 셀 위치 (0~8)

                // [수정 완료] Key 값의 공백을 제거하여 HTML name과 정확히 매칭
                String key = "goal[" + i + "][" + j + "]";
                String newContent = goalParams.get(key);

                if (newContent != null) {
                    cell.updateContent(newContent); // Cell 엔티티의 변경 감지 발생
                }
            }
        }
        // Transactional 덕분에 save 호출 없이도 메서드 종료 시 DB에 반영됨
        return mandalart.getId();
    }

    /**
     * 3. 수정 폼 진입 시 기존 데이터를 Map으로 변환
     */
// MandalartService.java 내부의 getCellMap 메서드를 아래와 같이 수정해보세요.
    @Transactional(readOnly = true)
    public Map<String, String> getCellMap(Long mandalartId) {
        Mandalart mandalart = findMandalart(mandalartId);
        Map<String, String> cellMap = new HashMap<>();

        List<Category> categories = mandalart.getCategories();

        for (Category category : categories) {
            // [수정] 루프 인덱스 i 대신, DB의 position 값을 직접 가져옵니다.
            int pos = category.getPosition();

            for (Cell cell : category.getCells()) {
                // HTML의 i와 매칭될 수 있도록 position 값을 키로 사용합니다.
                String key = "goal[" + pos + "][" + cell.getIndex() + "]";
                cellMap.put(key, cell.getContent());
            }
        }
        return cellMap;
    }

    /**
     * 4. 헬퍼 메서드 및 기타 기능
     */
    public Mandalart findMandalart(Long mandalartId) {
        return mandalartRepository.findById(mandalartId)
                .orElseThrow(() -> new IllegalArgumentException("해당 만다라트가 없습니다."));
    }

    @Transactional
    public void toggleCell(Long cellId) {
        Cell cell = cellRepository.findById(cellId)
                .orElseThrow(() -> new IllegalArgumentException("해당 셀이 없습니다."));
        cell.toggleCompletion();
    }

    public int getCompletedCount(Long categoryId) {
        return (int) cellRepository.findByCategoryId(categoryId).stream()
                .filter(Cell::getIsCompleted)
                .count();
    }

    private int calculateFlatIndex(int blockIdx, int cellIdx) {
        return (blockIdx / 3) * 27 + (blockIdx % 3) * 3 + (cellIdx / 3) * 9 + (cellIdx % 3);
    }
}