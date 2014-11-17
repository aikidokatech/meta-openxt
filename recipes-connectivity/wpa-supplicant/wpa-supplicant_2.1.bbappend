PR = "openxt-01"

do_configure_append () {
# Config is already copied in OE recipe
#    install -m 0755 ${S}/defconfig .config
# Ensure the settings make it into the config file.
    echo "CONFIG_CTRL_IFACE_DBUS=y" >> .config
    echo "CONFIG_CTRL_IFACE_DBUS_NEW=y" >> .config 
    echo "CONFIG_TLS=openssl" >> .config
    echo "CONFIG_DRIVER_NL80211=y" >> .config
    echo "CONFIG_LIBNL20=y" >> .config
    echo "CONFIG_BGSCAN=y" >> .config
    echo "CONFIG_BGSCAN_SIMPLE=y" >> .config
}
