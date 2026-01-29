# --- Estágio 1: Build (Compilação Angular) ---
FROM public.ecr.aws/docker/library/node:18-alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build -- --configuration production

# --- Estágio 2: Servidor Nginx ---
FROM public.ecr.aws/docker/library/nginx:alpine

# Copia o build do Angular para o Nginx
COPY --from=build /app/dist/scad-ui/browser /usr/share/nginx/html

# Copia a configuração do Nginx (que deve estar na pasta scad-ui)
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
