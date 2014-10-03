PR = "openxt-01"

PACKAGE_ARCH = "${MACHINE_ARCH}"
EXTRA_OEMAKE += "-j 1"
 
do_install_append_openxt-dom0() {
	echo "29 rt-brbridged" >> ${D}/etc/iproute2/rt_tables
	echo "60 rt-wlan0" >> ${D}/etc/iproute2/rt_tables
	echo "90 rt-ath0" >> ${D}/etc/iproute2/rt_tables
	echo "100 unreach" >> ${D}/etc/iproute2/rt_tables
}

do_install_append_openxt-ndvm() {
	echo "29 rt-brbridged" >> ${D}/etc/iproute2/rt_tables
	echo "60 rt-wlan0" >> ${D}/etc/iproute2/rt_tables
	echo "90 rt-ath0" >> ${D}/etc/iproute2/rt_tables
        echo "100 unreach" >> ${D}/etc/iproute2/rt_tables
}

