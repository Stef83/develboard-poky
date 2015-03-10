#!/bin/bash -eux

SSH_USER=${SSH_USERNAME:-vagrant}

echo "==> Installing packages to build a Yocto distribution"
apt-get -y install gawk wget git diffstat unzip texinfo gcc-multilib build-essential chrpath socat libsdl1.2-dev xterm make xsltproc docbook-utils fop dblatex xmlto autoconf automake libtool libglib2.0-dev lzop

echo "==> Checkout develboard SDK"
ssh-keyscan github.com >> /home/${SSH_USER}/.ssh/known_hosts
su ${SSH_USER} -c "git clone https://github.com/develersrl/develboard-kernel.git /home/${SSH_USER}/Desktop/develboard-kernel"
su ${SSH_USER} -c "git clone https://github.com/develersrl/develboard-poky.git /home/${SSH_USER}/Desktop/develboard-poky"
