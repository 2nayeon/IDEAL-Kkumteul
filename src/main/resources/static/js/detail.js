/**
 * detail.js
 */
document.addEventListener('DOMContentLoaded', () => {
    init();
});

async function init() {
    const pathSegments = window.location.pathname.split('/');
    const categoryId = pathSegments.filter(segment => segment !== "").pop();

    if (!categoryId || isNaN(categoryId)) return;

    const data = await fetchCategoryData(categoryId);
    if (data) {
        data.categoryId = categoryId;
        renderMandalart(data);
    }
}

async function fetchCategoryData(id) {
    try {
        const response = await fetch(`/api/mandalart/category/${id}`);
        return await response.json();
    } catch (error) {
        console.error("데이터 로드 실패:", error);
        return null;
    }
}

function renderMandalart(data) {
    const titleText = data.title || "목표 정보 없음";
    document.getElementById('category-title').innerText = titleText;
    document.getElementById('center-cell').innerText = titleText;

    const subCells = document.querySelectorAll('.sub-cell');
    subCells.forEach((cell, index) => {
        // 텍스트 반영 (데이터가 없으면 동그라미 표시)
        cell.innerText = (data.items && data.items[index]) ? data.items[index] : "-";

        // 서버에서 받아온 완료 상태가 true라면 'completed' 클래스 부여
        if (data.completedList && data.completedList[index] === true) {
            cell.classList.add('completed');
        }

        // 클릭 이벤트 연결
        cell.onclick = () => toggleCell(data.categoryId, index, cell);
    });

    updateGrowthUI(data);
}

async function toggleCell(categoryId, cellIndex, cellElement) {
    try {
        const response = await fetch(`/api/mandalart/category/${categoryId}/cell/${cellIndex}/toggle`, {
            method: 'POST'
        });
        const result = await response.json();

        if (result.success) {
            // [핵심] 서버 응답 결과(isCompleted)에 따라 초록색 배경/취소선 토글
            cellElement.classList.toggle('completed', result.isCompleted);

            // 전체 달성 개수(achieved)를 바탕으로 이미지 및 게이지 업데이트
            updateGrowthUI({
                achieved: result.achieved
            });
        }
    } catch (error) {
        console.error("토글 실패:", error);
    }
}

function updateGrowthUI(data) {
    const imgElement = document.getElementById('growth-img');
    const textElement = document.getElementById('achievement-text');
    const progressFill = document.getElementById('progress-fill');
    const percentText = document.getElementById('percent-text');

    // 달성 개수 확인 (기본값 0)
    const achieved = data.achieved || 0;
    const imgKey = getImgKeyFromTitle();

    // [수정 포인트] 달성 개수가 0개일 때는 이미지를 보여주지 않음
    if (achieved === 0) {
        imgElement.style.display = 'none'; // 이미지 숨김
        imgElement.src = '';              // 경로 초기화
        textElement.innerText = `아직 시작 전이에요! 첫 발을 내디뎌보세요.`;
    } else {
        // 1개 이상 달성 시 이미지 표시
        imgElement.style.display = 'block';
        // stage_0.png부터 시작하게 하려면 (achieved - 1)을 사용합니다.
        // 현재 로직상 1개 클릭 시 0번 이미지가 나오도록 설정:
        imgElement.src = `/images/${imgKey}/stage_${achieved - 1}.png`;

        imgElement.onerror = () => { imgElement.src = '/images/default/stage_0.png'; };
        textElement.innerText = `현재 8단계 중 ${achieved}단계 성취!`;
    }

    // 진행바 업데이트
    const percent = Math.min((achieved / 8) * 100, 100);

    setTimeout(() => {
        if (progressFill) progressFill.style.width = `${percent}%`;
        if (percentText) percentText.innerText = `${Math.floor(percent)}% 피어나는 중`;
    }, 100);
}

function getImgKeyFromTitle() {
    const title = document.getElementById('category-title').innerText;
    if (title.includes("독서")) return "book";
    if (title.includes("운동")) return "gym";
    if (title.includes("공부")) return "study";
    if (title.includes("경제")) return "economy";
    if (title.includes("여행")) return "travel";
    if (title.includes("취미")) return "hobby";
    if (title.includes("가족")) return "family";
    if (title.includes("자기관리")) return "selfcare";
    return "default";
}

// 돌아가기 버튼 이벤트는 HTML에서 onclick="history.back()"을 쓰거나 아래처럼 유지
const btnBack = document.getElementById('btn-back');
if (btnBack) {
    btnBack.addEventListener('click', () => {
        window.location.href = '/';
    });
}