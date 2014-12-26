require qemu-dm.inc

SRCREV_source = "${AUTOREV}"

DEPENDS += " pciutils-static "

EXTRA_OECONF += " --static --disable-syslog "
