//
// Created by 杨充 on 2024/1/22.
//


#include <stdio.h>
#include <stdlib.h>

void initPrintMenu();

int main() {
    while (1) {
        initPrintMenu();
        getchar();
    }
    return 0;
}

void initPrintMenu() {
    printf("*********************************\n");
    printf("*\t学生成绩管理系统\t*\n");
    printf("*********************************\n");
    printf("*\t请选择功能列表\t\t*\n");
    printf("*********************************\n");
    printf("*\t1.录入学生信息\t\t*\n");
    printf("*\t2.打印学生信息\t\t*\n");
    printf("*\t3.统计学生人数\t\t*\n");
    printf("*\t4.查找学生信息\t\t*\n");
    printf("*\t5.修改学生信息\t\t*\n");
    printf("*\t6.删除学生信息\t\t*\n");
    printf("*\t7.学生成绩排序\t\t*\n");
    printf("*\t8.推出学生系统\t\t*\n");
    printf("*********************************\n");
}
