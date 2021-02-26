package cn.wuerli;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please input the target file path:");
        String path = sc.nextLine();
        Detective.porcess(path);
    }
}
