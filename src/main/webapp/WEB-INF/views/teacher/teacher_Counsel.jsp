<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>teacher_Counsel</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/teacher/teacherCounsel.css'/>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
</head>

<body>
	<jsp:include page="../common/teacher_menubar.jsp" />
    <div class="whole_body">
        <div class="use_body1">
            <div class="counsel-calendar">
                <div class="wrap">
                    <div class="header">
                        <div>
                            2024년
                            <select id="monthSelect"></select>월
                        </div>
                    </div>
                    <br />
                    <table align="center" width="500" style="text-align: center" id="calendar">
                        <thead>
                            <tr>
                                <th>일</th>
                                <th>월</th>
                                <th>화</th>
                                <th>수</th>
                                <th>목</th>
                                <th>금</th>
                                <th>토</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- 날짜(js) -->
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="body-write">
                <div class="counsel-apply">상담 신청 내역</div>
                <div id="selectedDate" class="selectdd">날짜 선택</div>
                <img src="<c:url value='/resources/img/teacher/free-icon-calendar-check-7955802 4.png'/>" id="dateIcon" style="cursor: pointer;"
                    class="img-calendar" />
                <input type="text" id="datepicker" style="display:none;" />
            </div>
            <div class="counsel-information">
                <div class="information-info">
                    <div>시간/장소</div>
                    <div>자녀 이름</div>
                    <div>전화번호</div>
                    <div>상담내용</div>
                </div>
                <hr>
                <div class="real-information-info" id="consultationList"></div>
                <!--상담 등록-->
                <div id="modal" class="modal">
                    <div class="modal-content">
                        <form action="">
                            <span class="close" id="closeBtn">&times;</span>
                            <table>
                                <tr>
                                    <td>상담 시간</td>
                                    <td><input type="time" id="consultationTime" style="width: 194px; height: 44px;" /></td>
                                </tr>
                                <tr>
                                    <td>상담 장소</td>
                                    <td><input type="text" id="consultationLocation" placeholder="ex) 2층교무실" /></td>
                                </tr>
                            </table>
                            <button type="button" id="saveBtn">저장</button>
                            <button type="button" id="deleteBtn"
                                style="background-color: red; color: white; margin-top: 10px; display: none;">삭제</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Date Range Picker JS -->
    <script src="https://cdn.jsdelivr.net/npm/jquery/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/moment/min/moment.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            // Date Range Picker 초기화
            $('#datepicker').daterangepicker({
                singleDatePicker: true,
                showDropdowns: true,
                autoUpdateInput: false,
                autoApply: true,
                startDate: moment(),
                locale: {
                    format: 'YYYY-MM-DD'
                },
                drops: 'up',
                opens: 'center'
            }, function (start) {
                $('#selectedDate').text(start.format('YYYY-MM-DD'));
            });

            // img 클릭 시 달력 열기 및 위치 조정
            $('#dateIcon').on('click', function () {
                $('#datepicker').focus();
                setTimeout(function () {
                    var iconPosition = $('#dateIcon').offset();
                    var datepickerHeight = $('.daterangepicker').outerHeight();
                    $('.daterangepicker').css({
                        top: iconPosition.top - datepickerHeight - 10 + "px",
                        left: iconPosition.left + "px"
                    });
                }, 10);
            });
        });

        $(document).ready(function () {
            const fixedYear = 2024;
            initializeMonthSelector();
            createCalendar(fixedYear);

            $('#monthSelect').on('change', function () {
                createCalendar(fixedYear);
            });

            $('#calendar').on('click', 'td', function () {
                const day = $(this).text();
                if (day) {
                    const month = $('#monthSelect').val().padStart(2, '0');
                    const consultationDate = `${fixedYear}-${month}-${day.padStart(2, '0')}`;
                    $('#selectedDate').text(consultationDate); // Update selected date display
                    $('#modal').css('display', 'block');
                    selectedCell = $(this);
                }
            });

            $('#closeBtn').on('click', function () {
                $('#modal').css('display', 'none');
            });

            $('#saveBtn').on('click', function () {
                writeSchedule();
            });

            $('#deleteBtn').on('click', function () {
                deleteSchedule(); // Call delete function
            });

            $(window).on('click', function (event) {
                if ($(event.target).is('#modal')) {
                    $('#modal').css('display', 'none');
                }
            });
        });

        let selectedCell;
        let selectedButton; // Declare selectedButton variable

        function initializeMonthSelector() {
            for (let month = 1; month <= 12; month++) {
                $('#monthSelect').append(new Option(month, month));
            }
            $('#monthSelect').val(new Date().getMonth() + 1);
            $('#monthSelect').css({
                'background-color': '#A98467',
                'text-align': 'center',
                'font-size': '30px'
            });
        }

        function createCalendar(year) {
            const month = $('#monthSelect').val();
            const daysInMonth = new Date(year, month, 0).getDate();
            const firstDay = new Date(year, month - 1, 1).getDay();
            const $tbody = $('#calendar tbody');
            $tbody.empty();

            let row = $('<tr></tr>');
            for (let i = 0; i < firstDay; i++) {
                row.append($('<td></td>'));
            }

            for (let day = 1; day <= daysInMonth; day++) {
                row.append($('<td></td>').text(day));
                if ((day + firstDay) % 7 === 0) {
                    $tbody.append(row);
                    row = $('<tr></tr>');
                }
            }

            while (row.children().length < 7) {
                row.append($('<td></td>'));
            }
            $tbody.append(row);
        }

        function writeSchedule() {
            const consultationTime = $('#consultationTime').val(); // Get the selected consultation time
            const consultationLocation = $('#consultationLocation').val();

            if (!consultationTime || !consultationLocation) {
                alert('모든 필드를 채워주세요.'); // Alert if fields are empty
                return;
            }

            const appointmentButtons = selectedCell.children('.appointment-time'); // Get existing buttons for the selected date

            if (appointmentButtons.length >= 3) { // Limit to 3 buttons per date
                alert('상담시간/장소는 최대 3개까지 등록 가능합니다.'); 
                return;
            }

            // Create new button with consultation details
            const button = $('<button class="appointment-time"></button>')
                .text(`${consultationTime} / ${consultationLocation}`)
                .on('click', function () {
                    $('#consultationTime').val(consultationTime);
                    $('#consultationLocation').val(consultationLocation);
                    $('#deleteBtn').show(); // Show delete button when appointment is selected
                    selectedButton = $(this); // Save reference to selected button
                });

            selectedCell.append(button); // Add button to selected cell
            $('#modal').css('display', 'none'); // Hide modal
            $('#consultationTime').val(''); // Clear input fields
            $('#consultationLocation').val('');
            $('#deleteBtn').hide(); // Hide delete button after saving
        }

        function deleteSchedule() {
            if (selectedButton) {
                selectedButton.remove(); // Remove the selected button from the cell
                selectedButton = null; // Clear the reference to the selected button
                $('#deleteBtn').hide(); // Hide delete button
                $('#consultationTime').val(''); // Clear input fields
                $('#consultationLocation').val('');
            }
        }
    </script>
</body>

</html>  