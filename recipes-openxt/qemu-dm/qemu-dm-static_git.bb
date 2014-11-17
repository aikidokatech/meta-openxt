require qemu-dm.inc

DEPENDS += " pciutils-static "

EXTRA_OECONF += " --static --disable-syslog "
