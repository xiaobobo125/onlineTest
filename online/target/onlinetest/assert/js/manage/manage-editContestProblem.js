/**
 * 模块化JavaScript
 **/
var manageEditContestProblemPage = {
    data:{
        contest: null,
        questions: [],
    },
    init: function (contest, questions) {
        manageEditContestProblemPage.data.contest = contest;
        manageEditContestProblemPage.data.questions = questions;

        //新增题目，弹出新增窗口
        $("#addQuestionBtn").click(function () {
            //输入框初始化数据
            // manageEditContestProblemPage.initAddQuestionData();
            $("#addQuestionModal").modal({
                keyboard : false,
                show : true,
                backdrop : "static"
            });
        });

        //新增题目，取消题目增加
        $('#cancelAddQuestionBtn').click(function(){
            $("#addQuestionModal").modal('hide');
        });

        //新增考试，确定增加考试
        $('#confirmAddQuestionBtn').click(function(){
            manageEditContestProblemPage.addQuestionAction();
        });

        //编辑题目，取消题目编辑
        $('#cancelUpdateQuestionBtn').click(function(){
            $("#updateQuestionModal").modal('hide');
        });

        //编辑题目，确定保存考试
        $('#confirmUpdateQuestionBtn').click(function(){
            manageEditContestProblemPage.updateQuestionAction();
        });



    },
    initAddQuestionData: function () {
        //初始化数据
        $('#questionTitle').val("");
        $('#questionContent').val("");
        $('#optionA').val("");
        $('#optionB').val("");
        $('#optionC').val("");
        $('#optionD').val("");
        $('#questionAnswer').val("");
        $('#questionParse').val("");
        $('#questionScore').val("");
    },
    checkAddQuestionData: function (soloSel,manySel,queAns,program) {
        return true;
    },
    addQuestionAction: function () {
        var soloSel = $('#soloSel').val();
        var manySel = $('#manySel').val();
        var queAns = $('#queAns').val();
        var program = $('#program').val();
        var space = $('#space').val();
        var sucErr = $('#sucErr').val();
        var contestId = manageEditContestProblemPage.data.contest.id;
        var subjectId = manageEditContestProblemPage.data.contest.subjectId;

        if (manageEditContestProblemPage.checkAddQuestionData(soloSel,manySel,queAns,program)) {
            $.ajax({
                url : app.URL.randomContestUrl(),
                type : "POST",
                <!-- 向后端传输的数据 -->
                data : {
                    soloSel: soloSel,
                    manySel: manySel,
                    queAns: queAns,
                    program: program,
                    space: space,
                    sucErr: sucErr,
                    contestId: contestId,
                    subjectId: subjectId,
                },
                success:function(result) {
                    if (result && result['success']) {
                        // 验证通过 刷新页面
                        window.location.reload();
                    } else {
                        $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示: </div>' +
                            '                <p>'+result.message+'</p>');
                        $('#loginModalErrorMessage').removeClass('hidden');
                    }
                },
                error:function(result){
                    $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                        '                <p>'+result.message+'</p>');
                    $('#loginModalErrorMessage').removeClass('hidden');
                }
            });
        }
    },
    //编辑题目模态框触发
    updateQuestionModalAction: function (index) {
        //编辑考试，弹出编辑窗口
        //console.log(index);
        //输入框初始化数据
        manageEditContestProblemPage.initUpdateQuestionData(index);
        $("#updateQuestionModal").modal({
            keyboard : false,
            show : true,
            backdrop : "static"
        });
    },
    initUpdateQuestionData: function (index) {
        //初始化数据
        $('#updateQuestionIndex').val(index);
        $('#updateQuestionTitle').val(questions[index].title);
        $('#updateQuestionContent').val(questions[index].content);
        var selectQuestionTypes = document.getElementById('updateQuestionType');
        for (var i = 0; i < selectQuestionTypes.length; i++) {
            if (selectQuestionTypes[i].value == questions[index].questionType) {
                selectQuestionTypes[i].selected = true;
            }
        }
        $('#updateOptionA').val(questions[index].optionA);
        $('#updateOptionB').val(questions[index].optionB);
        $('#updateOptionC').val(questions[index].optionC);
        $('#updateOptionD').val(questions[index].optionD);
        $('#updateQuestionAnswer').val(questions[index].answer);
        $('#updateQuestionParse').val(questions[index].parse);
        var selectQuestionDifficulty = document.getElementById('updateQuestionDifficulty');
        for (var i = 0; i < selectQuestionDifficulty.length; i++) {
            if (selectQuestionDifficulty[i].value == questions[index].difficulty) {
                selectQuestionDifficulty[i].selected = true;
            }
        }
        $('#updateQuestionScore').val(questions[index].score);
    },
    checkUpdateQuestionData: function (questionTitle, questionContent, questionType,
                                    optionA, optionB, optionC, optionD,
                                    questionAnswer, questionParse, questionDifficulty,
                                    questionScore) {
        //TODO::校验
        return true;

    },
    updateQuestionAction: function () {
        var index = $('#updateQuestionIndex').val();
        var questionTitle = $('#updateQuestionTitle').val();
        var questionContent = $('#updateQuestionContent').val();
        var questionType = $('#updateQuestionType').val();
        var optionA = $('#updateOptionA').val();
        var optionB = $('#updateOptionB').val();
        var optionC = $('#updateOptionC').val();
        var optionD = $('#updateOptionD').val();
        var questionAnswer = $('#updateQuestionAnswer').val();
        var questionParse = $('#updateQuestionParse').val();
        var questionDifficulty = $('#updateQuestionDifficulty').val();
        var questionScore = $('#updateQuestionScore').val();
        var contestId = manageEditContestProblemPage.data.contest.id;
        var subjectId = manageEditContestProblemPage.data.contest.subjectId;

        if (manageEditContestProblemPage.checkUpdateQuestionData(questionTitle, questionContent,
                questionType, optionA,optionB, optionC, optionD, questionAnswer, questionParse,
                questionDifficulty, questionScore)) {
            $.ajax({
                url : app.URL.updateQuestionUrl(),
                type : "POST",
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data : JSON.stringify({
                    id: questions[index].id,
                    title: questionTitle,
                    content: questionContent,
                    questionType: questionType,
                    optionA: optionA,
                    optionB: optionB,
                    optionC: optionC,
                    optionD: optionD,
                    answer: questionAnswer,
                    parse: questionParse,
                    subjectId: subjectId,
                    contestId: contestId,
                    score: questionScore,
                    difficulty: questionDifficulty,
                }),
                success:function(result) {
                    if (result && result['success']) {
                        // 验证通过 刷新页面
                        window.location.reload();
                    } else {
                        $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                            '                <p>'+result.message+'</p>');
                        $('#loginModalErrorMessage').removeClass('hidden');
                    }
                },
                error:function(result){
                    $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                        '                <p>'+result.message+'</p>');
                    $('#loginModalErrorMessage').removeClass('hidden');
                }
            });
        }
    },
    deleteQuestionAction: function (index) {
        $.ajax({
            url : app.URL.deleteQuestion_contentUrl()+index,
            type : "GET",
            dataType: "json",
            contentType : "application/json;charset=UTF-8",
            success:function(result) {
                if (result && result['success']) {
                    // 验证通过 刷新页面
                    window.location.reload();
                } else {
                    $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                        '                <p>'+result.message+'</p>');
                    $('#loginModalErrorMessage').removeClass('hidden');
                }
            },
            error:function(result){
                $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                    '                <p>'+result.message+'</p>');
                $('#loginModalErrorMessage').removeClass('hidden');
            }
        });
    }
};