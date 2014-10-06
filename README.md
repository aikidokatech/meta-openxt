meta-openxt
===========

This is a heavy WIP on converting OpenXT to an OE layer, while also bringing it up-to-date with OE daisy.  It
is not yet using the master branch of OE Core, but daisy.  I am currently using Bitbake 1.24.0 but was down
at 1.22 for most of the work.  There was no need to change but I did so thinking I had hit a bitbake issue.  At
this time the eglibc-locale package actually builds the binary locale files.  This was the brick wall I hit
trying to do this with the legacy OE stuff.

I am now attempting to get the xenclient images to fully parse out before attempting any actual building.  I
have updated a lot of bbappend files and copied in some old OE recipes as the patches were more than I could 
quickly go over.  A complete list is below.  I am also attempting to utilize Phil's meta-measured layer and 
need to check in with him on if that is a good idea at this time or not.

The following layers are in use:  
>  /home/aikidoka/oe-core/meta  
>  /home/aikidoka/oe-core/meta-java  
>  /home/aikidoka/oe-core/meta-measured  
>  /home/aikidoka/oe-core/meta-openembedded/meta-gnome  
>  /home/aikidoka/oe-core/meta-openembedded/meta-networking  
>  /home/aikidoka/oe-core/meta-openembedded/meta-oe  
>  /home/aikidoka/oe-core/meta-openembedded/meta-xfce  
>  /home/aikidoka/oe-core/meta-openxt  
>  /home/aikidoka/oe-core/meta-selinux  
  
Current I am working to get the following command to complete parsing (ignoring QA Issues for now):  
`MACHINE="openxt-dom0" DISTRO="openxt" bitbake -e -n -k xenclient-installer-image`

Recipes ported to OE daisy  
----------------------------------------------------------  
- dnsmasq 2.55 -> 2.68  
- readline 6.2 -> 6.3  
- pango 1.29.4 -> 1.36.2  
- tirpc 0.2.2 -> 0.2.4  
- wpa-supplicant 0.7.3 -> 2.1  
- gdk-pixbuf 2.24.1 -> 2.30.3  
- iproute2 3.2.0 -> 3.12.0  
- gdb 7.4 -> 7.6.2  
- util-linux 2.21 -> 2.24.1  
- xterm 277 -> 303  
- libsm 1.2.1 -> 1.2.2  
- libpciaccess 0.12.902 -> 0.13.2  
- bridge-utils 1.4 -> 1.5  
- glib 2.30.3 -> 2.38.2  
- libpam 1.1.5 -> 1.1.6  
- opkg svn rev -> 0.2.1  
- e2fsprogs 1.42.1 -> 1.42.9  
- xserver-org 1.11.2 -> 1.15.0  
- dbus 1.2.28 -> 1.6.18  
- libgcc 4.6 -> 4.8  
- xfwm4 4.8.3 -> git rev  
- xfce4-settings 4.8.3 -> git rev  
- xfce4-session 4.8.3 -> 4.10.1  
- coreutils 8.14 -> 8.22  

Recipes removed  
----------------------------------------------------------  
- eglibc  
- pigz  
- jamvm  
- rsync  
- libecj-bootstrap  
- libklavier  
- base-passwd  
- opkg-utils  
- vim-tiny  
- mtools  

Recipes copied from OE legacy (until patches are ported)  
----------------------------------------------------------  
- lvm2  
- networkmanager  
- network-manager-applet  
- alsa-utils  

Recipies removed so far as they are in meta-measured  
----------------------------------------------------------  
- trousers  
