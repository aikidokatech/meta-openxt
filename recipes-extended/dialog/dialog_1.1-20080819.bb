SRC_URI[md5sum] = "3caebd641a9f337b980becb4444336c5"
SRC_URI[sha256sum] = "c5d49b39c5998bcecd124c05cc6f096d22ccdc378ad455214611ae41a2f4b7d9"
require dialog.inc

SRC_URI += "file://cross-compile-hack.patch;patch=1 \
            file://multibyte-password.patch;patch=1 \
            file://freeze.patch;patch=1"
EXTRA_OECONF += "--enable-widec --with-ncursesw"
