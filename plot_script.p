set grid
set term jpeg medium size 1024,768
set xdata time
set timefmt "%m/%d/%y"
set format x "%m/%d"
set timefmt "%m/%d/%y %H:%M"
plot "data" using 1:3


set timefmt "%H:%M"
set xdata time
set output "mean_Leq_s.jpg"
plot "day_1_mean_gnu.csv" u 52:2 ti "mon" w l, "day_2_mean_gnu.csv" u 52:2 ti "tue" w l, "day_3_mean_gnu.csv" u 52:2 ti "wed" w l, "day_4_mean_gnu.csv" u 52:2 ti "thu" w l, "day_5_mean_gnu.csv" u 52:2 ti "fri" w l, "day_6_mean_gnu.csv" u 52:2 ti "sat" w l, "day_7_mean_gnu.csv" u 52:2 ti "sun" w l
set output "var_Leq_s.jpg"
plot "day_1_var_gnu.csv" u 52:2 ti "mon" w l, "day_2_var_gnu.csv" u 52:2 ti "tue" w l, "day_3_var_gnu.csv" u 52:2 ti "wed" w l, "day_4_var_gnu.csv" u 52:2 ti "thu" w l, "day_5_var_gnu.csv" u 52:2 ti "fri" w l, "day_6_var_gnu.csv" u 52:2 ti "sat" w l, "day_7_var_gnu.csv" u 52:2 ti "sun" w l

set output "mean_Leq_n.jpg"
plot "day_1_mean_gnu.csv" u 52:3 ti "mon" w l, "day_2_mean_gnu.csv" u 52:3 ti "tue" w l, "day_3_mean_gnu.csv" u 52:3 ti "wed" w l, "day_4_mean_gnu.csv" u 52:3 ti "thu" w l, "day_5_mean_gnu.csv" u 52:3 ti "fri" w l, "day_6_mean_gnu.csv" u 52:3 ti "sat" w l, "day_7_mean_gnu.csv" u 52:3 ti "sun" w l
set output "var_Leq_n.jpg"
plot "day_1_var_gnu.csv" u 52:3 ti "mon" w l, "day_2_var_gnu.csv" u 52:3 ti "tue" w l, "day_3_var_gnu.csv" u 52:3 ti "wed" w l, "day_4_var_gnu.csv" u 52:3 ti "thu" w l, "day_5_var_gnu.csv" u 52:3 ti "fri" w l, "day_6_var_gnu.csv" u 52:3 ti "sat" w l, "day_7_var_gnu.csv" u 52:3 ti "sun" w l

set output "mean_VLtot.jpg"
plot "day_1_mean_gnu.csv" u 52:42 ti "mon" w l, "day_2_mean_gnu.csv" u 52:42 ti "tue" w l, "day_3_mean_gnu.csv" u 52:42 ti "wed" w l, "day_4_mean_gnu.csv" u 52:42 ti "thu" w l, "day_5_mean_gnu.csv" u 52:42 ti "fri" w l, "day_6_mean_gnu.csv" u 52:42 ti "sat" w l, "day_7_mean_gnu.csv" u 52:42 ti "sun" w l
set output "var_VLtot.jpg"
plot "day_1_var_gnu.csv" u 52:42 ti "mon" w l, "day_2_var_gnu.csv" u 52:42 ti "tue" w l, "day_3_var_gnu.csv" u 52:42 ti "wed" w l, "day_4_var_gnu.csv" u 52:42 ti "thu" w l, "day_5_var_gnu.csv" u 52:42 ti "fri" w l, "day_6_var_gnu.csv" u 52:42 ti "sat" w l, "day_7_var_gnu.csv" u 52:42 ti "sun" w l

set output "mean_VPtot.jpg"
plot "day_1_mean_gnu.csv" u 52:45 ti "mon" w l, "day_2_mean_gnu.csv" u 52:45 ti "tue" w l, "day_3_mean_gnu.csv" u 52:45 ti "wed" w l, "day_4_mean_gnu.csv" u 52:45 ti "thu" w l, "day_5_mean_gnu.csv" u 52:45 ti "fri" w l, "day_6_mean_gnu.csv" u 52:45 ti "sat" w l, "day_7_mean_gnu.csv" u 52:45 ti "sun" w l
set output "var_VPtot.jpg"
plot "day_1_var_gnu.csv" u 52:45 ti "mon" w l, "day_2_var_gnu.csv" u 52:45 ti "tue" w l, "day_3_var_gnu.csv" u 52:45 ti "wed" w l, "day_4_var_gnu.csv" u 52:45 ti "thu" w l, "day_5_var_gnu.csv" u 52:45 ti "fri" w l, "day_6_var_gnu.csv" u 52:45 ti "sat" w l, "day_7_var_gnu.csv" u 52:45 ti "sun" w l

set output "mean_VTOTtot.jpg"
plot "day_1_mean_gnu.csv" u 52:49 ti "mon" w l, "day_2_mean_gnu.csv" u 52:49 ti "tue" w l, "day_3_mean_gnu.csv" u 52:49 ti "wed" w l, "day_4_mean_gnu.csv" u 52:49 ti "thu" w l, "day_5_mean_gnu.csv" u 52:49 ti "fri" w l, "day_6_mean_gnu.csv" u 52:49 ti "sat" w l, "day_7_mean_gnu.csv" u 52:49 ti "sun" w l
set output "var_VTOTtot.jpg"
plot "day_1_var_gnu.csv" u 52:49 ti "mon" w l, "day_2_var_gnu.csv" u 52:49 ti "tue" w l, "day_3_var_gnu.csv" u 52:49 ti "wed" w l, "day_4_var_gnu.csv" u 52:49 ti "thu" w l, "day_5_var_gnu.csv" u 52:49 ti "fri" w l, "day_6_var_gnu.csv" u 52:49 ti "sat" w l, "day_7_var_gnu.csv" u 52:49 ti "sun" w l

