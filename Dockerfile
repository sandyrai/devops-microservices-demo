FROM jenkins/jenkins:lts

USER root

# Install OpenJDK 17
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    update-alternatives --set java /usr/lib/jvm/java-17-openjdk-amd64/bin/java && \
    update-alternatives --set javac /usr/lib/jvm/java-17-openjdk-amd64/bin/javac

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

# Install docker-compose
RUN curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose && \
    chmod +x /usr/local/bin/docker-compose && \
    ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

RUN curl -fsSL https://github.com/krallin/tini/releases/download/v0.19.0/tini-static -o /sbin/tini && chmod +x /sbin/tini

ENTRYPOINT ["/sbin/tini", "--", "/usr/local/bin/jenkins.sh"]

USER jenkins
