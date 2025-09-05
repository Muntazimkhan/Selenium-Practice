package test;

public class MasterTestRunner {
    public static void main(String[] args) {
        int total = 0;
        int passed = 0;
        int failed = 0;

        String[] tests = {
                "FoodItem",
                "ManageCoupons",
                "manageUsers",
                "manageStaff",
                "ManageOrders",
                "ManageRestaurants",
                "ManageDeal",
                "ManageCategories",
                "ManageAddons",
                "logoutTest",
                "LoginTest",
                "foodVarieties"
        };

        for (String test : tests) {
            total++;
            try {
                System.out.println("Running: " + test);
                Class.forName("test." + test).getMethod("main", String[].class)
                        .invoke(null, (Object) new String[]{});
                passed++;
            } catch (Exception e) {
                System.out.println("❌ FAILED: " + test);
                e.printStackTrace();
                failed++;
            }
        }

        System.out.println("\n===================");
        System.out.println("Test Run Summary:");
        System.out.println("Total: " + total);
        System.out.println("Passed: ✅ " + passed);
        System.out.println("Failed: ❌ " + failed);
        System.out.println("===================");
    }
}
