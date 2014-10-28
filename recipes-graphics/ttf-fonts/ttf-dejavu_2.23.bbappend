PR = "openxt-01"

pkg_postinst_${PN}() {
    if [ -n "$D" ]; then
        exit 0 
    fi
}
