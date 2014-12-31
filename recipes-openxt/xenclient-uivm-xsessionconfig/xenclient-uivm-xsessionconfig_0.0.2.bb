
SRC_URI = "file://xinitrc \
	   file://xfce4-keyboard-shortcuts.xml \
	   file://xfwm4.xml \
	   file://helpers.rc \
           file://workspaces.xml \
	   file://session.xbel \
	   file://config \
	   file://framebuffer.conf \
	   file://Xdefaults \
	   file://default \
	   file://default.keyring \
           file://midori_login.sh \
           file://midori_report.sh \
           file://nm-applet-wrapper \
           file://nm-applet-launcher \
           file://start-nm-applet \
           file://v4v.modutils \
           file://xenfb2.modutils \
           file://xenkbd.modutils \
	   file://xdg-open \
	   file://Xft.xrdb \
           file://xsettings.xml \
           file://gtkrc \
           file://footer_bar_bgslice.png \
           file://xfwm4.tar.gz \
           file://custom-WebBrowser.desktop \
           file://custom-MailReader.desktop \
           file://custom-global.scm \
           file://custom-toolbar.scm \
           file://get-language \
           file://uim-toolbar-gtk-wrapper \
           file://language-sync \
           file://nm-backend-sync \
           file://keyboard \
"

RDEPENDS_${PN} += "xdotool"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${TOPDIR}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PR = "r26"

FILES_${PN} = "/etc/* /home/root/.xfce4/* /home/root/.config/* /home/root/.Xdefaults /home/root/.gnome2/keyrings/* /home/root/.local /home/root/.themes /home/root/.uim.d /usr/bin"

do_install () {
	   install -d ${D}/home/root/.xfce4
	   install -m 644 ${WORKDIR}/helpers.rc ${D}/home/root/.xfce4/

	   install -d ${D}/home/root/.config/xfce4
	   install -m 644 ${WORKDIR}/helpers.rc ${D}/home/root/.config/xfce4/
	   install -m 755 ${WORKDIR}/xinitrc ${D}/home/root/.config/xfce4/
	   install -m 644 ${WORKDIR}/Xft.xrdb ${D}/home/root/.config/xfce4/

	   install -d ${D}/home/root/.config/xfce4/xfconf/xfce-perchannel-xml
	   install -m 644 ${WORKDIR}/xfce4-keyboard-shortcuts.xml ${D}/home/root/.config/xfce4/xfconf/xfce-perchannel-xml/
	   install -m 644 ${WORKDIR}/xfwm4.xml ${D}/home/root/.config/xfce4/xfconf/xfce-perchannel-xml/
	   install -m 644 ${WORKDIR}/xsettings.xml ${D}/home/root/.config/xfce4/xfconf/xfce-perchannel-xml/

	   # Work around https://bugzilla.xfce.org/show_bug.cgi?id=8056
	   sed -i 's/&lt;Shift&gt;&lt;Control&gt;/\&lt;Primary\&gt;\&lt;Shift\&gt;/g' ${D}/home/root/.config/xfce4/xfconf/xfce-perchannel-xml/xfce4-keyboard-shortcuts.xml

	   install -d ${D}/home/root/.config/xfce4/mcs_settings
	   install -m 644 ${WORKDIR}/workspaces.xml ${D}/home/root/.config/xfce4/mcs_settings/

	   install -d ${D}/home/root/.local/share/xfce4/helpers
	   install -m 644 ${WORKDIR}/custom-WebBrowser.desktop ${D}/home/root/.local/share/xfce4/helpers
	   install -m 644 ${WORKDIR}/custom-MailReader.desktop ${D}/home/root/.local/share/xfce4/helpers

	   install -d ${D}/home/root/.config/midori
	   install -m 644 ${WORKDIR}/session.xbel ${D}/home/root/.config/midori/
	   install -m 644 ${WORKDIR}/config ${D}/home/root/.config/midori/

	   install -d ${D}/etc/modprobe.d
	   install -m 644 ${WORKDIR}/framebuffer.conf ${D}/etc/modprobe.d/

           install -d ${D}/etc/modutils
	   install -m 644 ${WORKDIR}/v4v.modutils ${D}/etc/modutils
	   install -m 644 ${WORKDIR}/xenfb2.modutils ${D}/etc/modutils
	   install -m 644 ${WORKDIR}/xenkbd.modutils ${D}/etc/modutils

	   install -m 644 ${WORKDIR}/Xdefaults ${D}/home/root/.Xdefaults

	   install -d ${D}/home/root/.gnome2/keyrings
	   install -m 600 ${WORKDIR}/default ${D}/home/root/.gnome2/keyrings/
	   install -m 600 ${WORKDIR}/default.keyring ${D}/home/root/.gnome2/keyrings/

	   install -d ${D}/home/root/.themes/XenClient/gtk-2.0
	   install -m 644 ${WORKDIR}/gtkrc ${D}/home/root/.themes/XenClient/gtk-2.0/
	   install -m 644 ${WORKDIR}/footer_bar_bgslice.png ${D}/home/root/.themes/XenClient/gtk-2.0/
	   install -d ${D}/home/root/.themes/XenClient/xfwm4
	   install -m 644 ${WORKDIR}/xfwm4/* ${D}/home/root/.themes/XenClient/xfwm4/

	   install -d ${D}/home/root/.uim.d/customs
	   install -m 644 ${WORKDIR}/custom-global.scm ${D}/home/root/.uim.d/customs/
	   install -m 644 ${WORKDIR}/custom-toolbar.scm ${D}/home/root/.uim.d/customs/

           install -d ${D}/usr/bin
           install -m 755 ${WORKDIR}/midori_login.sh ${D}/usr/bin/
           install -m 755 ${WORKDIR}/midori_report.sh ${D}/usr/bin/
           install -m 755 ${WORKDIR}/nm-applet-wrapper ${D}/usr/bin/
           install -m 755 ${WORKDIR}/nm-applet-launcher ${D}/usr/bin/
           install -m 755 ${WORKDIR}/start-nm-applet ${D}/usr/bin/
           install -m 755 ${WORKDIR}/xdg-open ${D}/usr/bin/
           install -m 755 ${WORKDIR}/get-language ${D}/usr/bin/
           install -m 755 ${WORKDIR}/uim-toolbar-gtk-wrapper ${D}/usr/bin/
           install -m 755 ${WORKDIR}/language-sync ${D}/usr/bin/
           install -m 755 ${WORKDIR}/nm-backend-sync ${D}/usr/bin/
           install -m 755 ${WORKDIR}/keyboard ${D}/usr/bin/
}