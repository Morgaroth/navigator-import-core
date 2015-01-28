
val s = "https://www.google.pl/maps/dir/50.0752533,19.890792/50.0687461,19.9221049/50.0558804,19.924079/@50.0715892,19.8947704,15z/data=!4m15!4m14!1m0!1m10!3m4!1m2!1d19.9285717!2d50.0634694!3s0x47165b0963a0cdc3:0xe61609c64dfb5607!3m4!1m2!1d19.9339983!2d50.0551194!3s0x47165b6d648200d3:0xecfbd9a830e98320!1m0!3e0"
val pattern = """^https://www\.google\.pl/maps/dir/(((\-?\d{2}\.\d{1,8}\,\-?\d{2}\.\d{2,8})/)|(([^@][^/]+)/))+(@\-?\d{2}\.\d{1,8}\,\-?\d{2}\.\d{2,8}),\d{1,2}z/data=((!4m\d{1,2}){2})((!1m\d{1,2}(!3m4!1m2!1d\-?\d{1,2}\.\d{1,10}!2d\-?\d{1,2}\.\d{1,10}!3s.*?)*)*)!3e0$""".r
val ret = pattern.findAllIn(s)
ret.groupCount
ret.group(1)
ret.group(2)
ret.group(3)
ret.group(4)
ret.group(5)
ret.group(6)
ret.group(7)
ret.group(8)
ret.group(9)
ret.group(1)
ret.group(11)