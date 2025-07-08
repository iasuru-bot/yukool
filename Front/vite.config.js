import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/products': 'http://localhost:8080',
      '/ingredients': 'http://localhost:8080',
      '/allergens': 'http://localhost:8080',
      '/additives': 'http://localhost:8080',
    }
  }
})
