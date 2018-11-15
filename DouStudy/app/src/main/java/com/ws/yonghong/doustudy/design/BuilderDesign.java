package com.ws.yonghong.doustudy.design;

import com.ws.yonghong.doustudy.db.Student;

public class BuilderDesign<T> {

    private int age = 10;
    private String name = "liuyonghong";
    private String sex = "男";

    public BuilderDesign(int age, String name, String sex) {
        this.age = age;
        this.name = name;
        this.sex = sex;
    }

    public static class Builder<T> {

        private int ageBuilder = 10;
        private String nameBuilder = "liuyonghong";
        private String sexBuilder = "男";

        public Builder() {
        }

        public Builder<T> setAge(int age) {
            this.ageBuilder = age;
            return this;
        }

        public Builder<T> setName(String name) {
            this.nameBuilder = name;
            return this;
        }

        public Builder<T> setSex(String sex) {
            this.sexBuilder = sex;
            return this;
        }

        public BuilderDesign<T> create() {
            return new BuilderDesign<T>(this.ageBuilder, this.nameBuilder, this.sexBuilder);
        }
    }

    @Override
    public String toString() {
        return "BuilderDesign{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public static void main(String[] args) {
        BuilderDesign<Student> mBuilderDesign = new BuilderDesign.Builder()
                .setAge(29)
                .setName("liu")
                .setSex("女")
                .create();
        System.out.println(mBuilderDesign.toString());
    }
}
