# Flujo Gitflow — Innovatech Solutions

## Estructura de Ramas

```
main ─────────────────────────────────────────────── (producción)
  │
  └── develop ────────────────────────────────────── (integración)
        │
        ├── feature/ms-auth-jwt ──────────────────── (nueva funcionalidad)
        ├── feature/ms-proyectos-crud ────────────── (nueva funcionalidad)
        │
        └── release/v1.0.0 ──────────────────────── (preparación release)
              │
              └── hotfix/fix-login-error ─────────── (corrección urgente)
```

---

## Ramas Principales

### `main` (Producción)
- **Propósito:** Contiene el código en **producción**. Cada commit en main es una versión desplegada.
- **Reglas de protección:**
  - ❌ No se permiten push directos
  - ✅ Requiere Pull Request con al menos **1 aprobación**
  - ✅ Requiere que todos los checks de CI/CD pasen
  - ✅ Requiere que la rama esté actualizada con `main` antes de merge
  - ✅ Solo merge desde `release/*` o `hotfix/*`
  - 🔒 Solo administradores pueden hacer merge

### `develop` (Integración)
- **Propósito:** Rama de integración donde se juntan las features terminadas.
- **Reglas de protección:**
  - ❌ No se permiten push directos
  - ✅ Requiere Pull Request con al menos **1 aprobación**
  - ✅ Requiere que los tests pasen (CI)
  - ✅ Solo merge desde `feature/*`

---

## Ramas de Soporte

### `feature/*` (Funcionalidades)
- **Nomenclatura:** `feature/<descripcion-corta>`
- **Ejemplos:** `feature/ms-auth-jwt`, `feature/crud-proyectos`, `feature/docker-compose`
- **Se crean desde:** `develop`
- **Se mergen a:** `develop`
- **Ciclo de vida:** Se eliminan después del merge

### `release/*` (Preparación de Release)
- **Nomenclatura:** `release/v<major>.<minor>.<patch>`
- **Ejemplos:** `release/v1.0.0`, `release/v1.1.0`
- **Se crean desde:** `develop`
- **Se mergen a:** `main` Y `develop`
- **Propósito:** Bug fixes de último momento, actualización de versiones

### `hotfix/*` (Correcciones Urgentes)
- **Nomenclatura:** `hotfix/<descripcion-del-fix>`
- **Ejemplos:** `hotfix/fix-login-error`, `hotfix/patch-security`
- **Se crean desde:** `main`
- **Se mergen a:** `main` Y `develop`

---

## Procedimiento Técnico: Pull Request de Feature a Develop

### Paso 1: Crear la rama feature
```bash
# Asegurarse de estar en develop actualizado
git checkout develop
git pull origin develop

# Crear la rama feature
git checkout -b feature/ms-auth-jwt
```

### Paso 2: Desarrollar la funcionalidad
```bash
# Hacer commits atómicos con mensajes descriptivos
git add .
git commit -m "feat(auth): implementar entidad Usuario y Rol"

git add .
git commit -m "feat(auth): implementar JwtService con generación y validación de tokens"

git add .
git commit -m "feat(auth): implementar AuthController con endpoints login/register"

git add .
git commit -m "test(auth): agregar tests unitarios para AuthService"
```

### Paso 3: Preparar el PR
```bash
# Actualizar con los últimos cambios de develop
git checkout develop
git pull origin develop
git checkout feature/ms-auth-jwt
git rebase develop

# Resolver conflictos si los hay
# git add .
# git rebase --continue

# Push de la rama
git push origin feature/ms-auth-jwt
```

### Paso 4: Crear el Pull Request en GitHub
1. Ir a GitHub → **Pull Requests** → **New Pull Request**
2. **Base:** `develop` ← **Compare:** `feature/ms-auth-jwt`
3. Llenar el template:

```markdown
## Descripción
Implementación del microservicio de autenticación con JWT.

## Cambios realizados
- [ ] Entidades: Usuario, Rol
- [ ] DTOs: LoginRequest, RegisterRequest, AuthResponse
- [ ] Servicios: AuthService, JwtService
- [ ] Controller: AuthController (/api/auth/login, /register, /me)
- [ ] Configuración: SecurityConfig, JwtAuthenticationFilter
- [ ] Tests unitarios

## Tipo de cambio
- [x] Nueva funcionalidad (feature)
- [ ] Corrección de bug (bugfix)
- [ ] Refactorización

## Checklist
- [x] El código compila sin errores
- [x] Los tests pasan localmente
- [x] Se siguieron las convenciones del proyecto
- [x] Se actualizó la documentación si aplica
```

### Paso 5: Revisión y Merge
1. El reviewer revisa el código y aprueba
2. Se verifica que el CI pase (compilación + tests)
3. Se hace **Squash and Merge** o **Merge commit**
4. Se elimina la rama feature

### Paso 6: Limpiar
```bash
# Después del merge, actualizar local
git checkout develop
git pull origin develop

# Eliminar la rama feature local
git branch -d feature/ms-auth-jwt
```

---

## Convención de Commits

Seguir el estándar **Conventional Commits**:

| Prefijo      | Uso                                    |
|-------------|----------------------------------------|
| `feat:`      | Nueva funcionalidad                   |
| `fix:`       | Corrección de bug                     |
| `docs:`      | Cambios en documentación              |
| `test:`      | Agregar o modificar tests             |
| `refactor:`  | Refactorización sin cambio funcional  |
| `chore:`     | Tareas de mantenimiento               |
| `ci:`        | Cambios en CI/CD                      |

**Formato:** `<tipo>(<scope>): <descripción corta>`

**Ejemplo:** `feat(proyectos): agregar endpoint de búsqueda por nombre`
