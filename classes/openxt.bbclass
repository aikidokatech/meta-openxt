# Probably should add an OpenXT version variable once it exists
OUT_DIR = "${TOPDIR}/build-output/OpenXT/raw"

do_ship() {
	# make the output directory if it does not exist yet
	mkdir -p "${OUT_DIR}"

	for t in cpio cpio.gz cpio.bz2 \
            tar tar.gz tar.bz2 \
            ext3 ext3.gz ext3.bz2 \
            ext3.vhd ext3.vhd.gz ext3.vhd.bz2 \
            xc.ext3 xc.ext3.gz xc.ext3.bz2 \
            xc.ext3.vhd xc.ext3.vhd.gz xc.ext3.vhd.bz2 \
            xc.ext3.bvhd xc.ext3.bvhd.gz xc.ext3.bvhd.bz2
	do
            if [ -f "${DEPLOY_DIR}/images/${MACHINE}/${PN}-${MACHINE}.$t" ]; then
                echo "${FRIENDLY_NAME} image: $t"
                cp -f "${DEPLOY_DIR}/images/${MACHINE}/${PN}-${MACHINE}.$t" "${OUT_DIR}/${FRIENDLY_NAME}-rootfs.i686.$t"
            fi
        done
}


