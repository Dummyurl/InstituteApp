package com.mksmcqapplicationtest.TabStructure.OtherOld;

import com.mksmcqapplicationtest.util.AppUtility;

public class CreateArrayList {
    String[] arrayList;
    String[] arrayListCode;
    int size = 0;
    int j = 0;

    public void getArrayList(String groupCode) {
        j = 0;
        for (int i = 0; i < AppUtility.childResponse.size(); i++) {
            if (groupCode.equals(AppUtility.childResponse.get(i).getGroupMenuCode())) {
                size++;
            }
        }
        arrayList = new String[size];
        for (int i = 0; i < AppUtility.childResponse.size(); i++) {
            if (groupCode.equals(AppUtility.childResponse.get(i).getGroupMenuCode())) {

                arrayList[j] = AppUtility.childResponse.get(i).getChildMenuName();
                j++;
            }
        }
    }

    public void getArrayListCode(String groupCode) {
        j = 0;
        for (int i = 0; i < AppUtility.childResponse.size(); i++) {
            if (groupCode.equals(AppUtility.childResponse.get(i).getGroupMenuCode())) {
                size++;
            }
        }
        arrayListCode = new String[size];
        for (int i = 0; i < AppUtility.childResponse.size(); i++) {
            if (groupCode.equals(AppUtility.childResponse.get(i).getGroupMenuCode())) {
                arrayListCode[j] = AppUtility.childResponse.get(i).getChildMenuCode();
                j++;
            }
        }
    }

    public String[] getExamArrayList(String groupCode) {

        getArrayList(groupCode);
//        if (AppUtility.IsTeacher.equals("T")) {
//            arrayList = new String[]{"MCQ Test", "MCQ Given Test", "Student MCQ Given Test", "Theory Test", "Extra Theory Test", "Send Marks"};
//        } else if (AppUtility.IsTeacher.equals("A")) {
//            arrayList = new String[]{"MCQ Test", "MCQ Given Test", "Student MCQ Given Test", "Theory Test", "Extra Theory Test", "Send Marks"};
//        } else if (AppUtility.IsTeacher.equals("S")) {
//            arrayList = new String[]{"MCQ Test", "MCQ Given Test", "Theory Test", "Extra Theory Test", "Show Marks"};
//        } else if (AppUtility.IsTeacher.equals("G")) {
//            arrayList = new String[]{"MCQ Test", "MCQ Given Test", "Theory Test", "Extra Theory Test"};
//        }
        return arrayList;
    }

    public String[] getExamArrayListCode(String groupCode) {
        getArrayListCode(groupCode);
        return arrayListCode;
    }


    public String[] getAttendanceArrayList(String groupCode) {
//        if (AppUtility.IsTeacher.equals("T")) {
//            arrayList = new String[]{"Mark Attendance", "Show Attendance", "Day Wise Report"};
//        } else if (AppUtility.IsTeacher.equals("A")) {
//            arrayList = new String[]{"Mark Attendance", "Show Attendance", "Day Wise Report"};
//        } else if (AppUtility.IsTeacher.equals("S")) {
//            arrayList = new String[]{"Show Attendance"};
//        } else if (AppUtility.IsTeacher.equals("G")) {
//            arrayList = new String[]{};
//        }
        getArrayList(groupCode);
        return arrayList;
    }

    public String[] getFeesArrayList(String groupCode) {
//        if (AppUtility.IsTeacher.equals("T")) {
//            arrayList = new String[]{"Add Fees", "Show Fees"};
//        } else if (AppUtility.IsTeacher.equals("A")) {
//            arrayList = new String[]{"Add Fees", "Show Fees"};
//        } else if (AppUtility.IsTeacher.equals("S")) {
//            arrayList = new String[]{"Show Fees"};
//        } else if (AppUtility.IsTeacher.equals("G")) {
//            arrayList = new String[]{};
//        }
        getArrayList(groupCode);
        return arrayList;
    }

    public String[] getActiveDeactiveArrayList(String groupCode) {
//        if (AppUtility.IsTeacher.equals("T")) {
//            arrayList = new String[]{"MCQ Test", "Theory Test", "Extra Theory Test", "Student", "Spoken English"};
//        } else if (AppUtility.IsTeacher.equals("A")) {
//            arrayList = new String[]{"MCQ Test", "Theory Test", "Extra Theory Test", "Student", "Spoken English"};
//        } else if (AppUtility.IsTeacher.equals("S")) {
//            arrayList = new String[]{};
//        } else if (AppUtility.IsTeacher.equals("G")) {
//            arrayList = new String[]{};
//        }
        getArrayList(groupCode);
        return arrayList;
    }

    public String[] getNotificationArrayList(String groupCode) {
//        if (AppUtility.IsTeacher.equals("T")) {
//            arrayList = new String[]{"Send Notification", "Show Notification", "Clear Notification"};
//        } else if (AppUtility.IsTeacher.equals("A")) {
//            arrayList = new String[]{"Send Notification", "Show Notification", "Clear Notification"};
//        } else if (AppUtility.IsTeacher.equals("S")) {
//            arrayList = new String[]{"Show Notification"};
//        } else if (AppUtility.IsTeacher.equals("G")) {
//            arrayList = new String[]{"Show Notification"};
//        }
        getArrayList(groupCode);
        return arrayList;
    }

    public String[] getGuestArrayList(String groupCode) {
//        if (AppUtility.IsTeacher.equals("T")) {
//            arrayList = new String[]{"Ac/Dc Theory Test", "Ac/Dc Extra Theory Test", "Guest Profile"};
//        } else if (AppUtility.IsTeacher.equals("A")) {
//            arrayList = new String[]{"Ac/Dc Theory Test", "Ac/Dc Extra Theory Test", "Guest Profile"};
//        } else if (AppUtility.IsTeacher.equals("S")) {
//            arrayList = new String[]{};
//        } else if (AppUtility.IsTeacher.equals("G")) {
//            arrayList = new String[]{};
//        }
        getArrayList(groupCode);
        return arrayList;
    }

    public String[] getUtilityArrayList(String groupCode) {
//        if (AppUtility.IsTeacher.equals("T")) {
//            arrayList = new String[]{"Move Theory Test", "Move Student", "Move Guest"};
//        } else if (AppUtility.IsTeacher.equals("A")) {
//            arrayList = new String[]{"Move Theory Test", "Move Student", "Move Guest"};
//        } else if (AppUtility.IsTeacher.equals("S")) {
//            arrayList = new String[]{};
//        } else if (AppUtility.IsTeacher.equals("G")) {
//            arrayList = new String[]{};
//        }
        getArrayList(groupCode);
        return arrayList;
    }

    public String[] getCommunicationArrayList(String groupCode) {
//        if (AppUtility.IsTeacher.equals("T")) {
//            arrayList = new String[]{"Queries", "Change Password", "Change Password Of All"};
//        } else if (AppUtility.IsTeacher.equals("A")) {
//            arrayList = new String[]{"Queries", "Change Password", "Change Password Of All"};
//        } else if (AppUtility.IsTeacher.equals("S")) {
//            arrayList = new String[]{"Queries", "Change Password"};
//        } else if (AppUtility.IsTeacher.equals("G")) {
//            arrayList = new String[]{"Queries", "Change Password"};
//        }
        getArrayList(groupCode);
        return arrayList;
    }

    public String[] getOtherArrayList(String groupCode) {
//        if (AppUtility.IsTeacher.equals("T")) {
//            arrayList = new String[]{"User Manual", "video Manual"};
//        } else if (AppUtility.IsTeacher.equals("A")) {
//            arrayList = new String[]{"User Manual", "video Manual"};
//        } else if (AppUtility.IsTeacher.equals("S")) {
//            arrayList = new String[]{"User Manual", "video Manual"};
//        } else if (AppUtility.IsTeacher.equals("G")) {
//            arrayList = new String[]{};
//        }
        getArrayList(groupCode);
        return arrayList;
    }

}
