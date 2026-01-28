# Estágio 1: Build da aplicação Angular
FROM node:18-alpine AS build

WORKDIR /app

# Copia os arquivos de dependências
COPY package*.json ./

# Instala as dependências
RUN npm install

# Copia o código fonte do projeto
COPY . .

# Compila a aplicação para produção
RUN npm run build -- --configuration production

# Estágio 2: Servidor Nginx para rodar a aplicação
FROM nginx:alpine

# Copia os arquivos compilados do estágio de build para a pasta do Nginx
# O caminho padrão do build é dist/scad-ui/browser (Angular 17+) ou dist/scad-ui
# Vamos tentar copiar de dist/scad-ui e, se falhar, o usuário precisará ajustar
COPY --from=build /app/dist/scad-ui/browser /usr/share/nginx/html

# Copia configuração customizada do Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
