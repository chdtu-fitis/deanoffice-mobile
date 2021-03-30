package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view;

public enum StudentDegree {

    Bachelor{
        @Override
        public int getMaxCoursesFirstSemester(){
            return 3;
        }

        @Override
        public int getMaxCoursesSecondSemester(){
            return 2;
        }

        @Override
        public int getMaxProfCoursesFirstSemester() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getMaxProfCoursesSecondSemester() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getMaxGenCoursesFirstSemester() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getMaxGenCoursesSecondSemester() {
            return Integer.MAX_VALUE;
        }
    },
    Master{
        @Override
        public int getMaxCoursesFirstSemester(){
            return 3;
        }

        @Override
        public int getMaxCoursesSecondSemester(){
            return 3;
        }

        @Override
        public int getMaxProfCoursesFirstSemester() {
            return 2;
        }

        @Override
        public int getMaxProfCoursesSecondSemester() {
            return 2;
        }

        @Override
        public int getMaxGenCoursesFirstSemester() {
            return 1;
        }

        @Override
        public int getMaxGenCoursesSecondSemester() {
            return 1;
        }

    };

    public abstract int getMaxCoursesFirstSemester();
    public abstract int getMaxCoursesSecondSemester();

    public abstract int getMaxProfCoursesFirstSemester();
    public abstract int getMaxProfCoursesSecondSemester();

    public abstract int getMaxGenCoursesFirstSemester();
    public abstract int getMaxGenCoursesSecondSemester();
}
