FROM docker:19.03.12

# Instalar Python y otras dependencias necesarias
RUN apk add --no-cache python3-dev py3-pip build-base

# Establecer el directorio de trabajo
WORKDIR /opt/calc

# Copiar los archivos del proyecto
COPY . .

# Instalar dependencias de Python
RUN pip3 install -r requirements.txt

# Configurar la aplicación Flask
ENV FLASK_APP=app/api.py

# Ejecutar la aplicación Flask
CMD ["flask", "run", "--host=0.0.0.0"]
