function redirectToDetail(title, studentId) {
    // URL 인코딩 처리
    const encodedTitle = encodeURIComponent(title);
    const encodedStudentId = encodeURIComponent(studentId);

    // 페이지 이동
    window.location.href = `homeworkDetail?title=${encodedTitle}&studentId=${encodedStudentId}`;
}
