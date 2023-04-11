FROM jenkins/jenkins:lts

USER root

RUN apt-get update && \
    apt-get install -y apt-transport-https \
                        ca-certificates \
                        curl \
                        gnupg2 \
                        software-properties-common && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add - && \
    add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable" && \
    apt-get update && \
    apt-get install -y docker-ce-cli && \
    groupadd docker && \
    usermod -aG docker jenkins

RUN curl -fsSL https://github.com/krallin/tini/releases/download/v0.19.0/tini-static -o /sbin/tini && chmod +x /sbin/tini

ENTRYPOINT ["/sbin/tini", "--", "/usr/local/bin/jenkins.sh"]

USER jenkins
