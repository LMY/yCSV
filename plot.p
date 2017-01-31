set grid
set term jpeg medium size 1024,768
set output "PT.jpg"
plot "md2a.data" u 1:2 ti "T" w l, "md2a.data" u 1:6 ti "P" w l


set output "E.jpg"
plot "md2a.data" u 1:3 ti "K" w l, "md2a.data" u 1:4 ti "U" w l, "md2a.data" u 1:5 ti "E" w l
