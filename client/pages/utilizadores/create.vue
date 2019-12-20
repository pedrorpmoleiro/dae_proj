<template>
    <div>
        <nuxt-link to="/utilizadores">Voltar</nuxt-link>
        <h1>Create a new Socio</h1>
        <form ref="form" @submit.prevent="create" :disabled="!isFormValid" v-model="isFormValid" lazy-validation>
            <v-text-field
                    v-model.trim="name"
                    :counter="30"
                    :rules="nameRules"
                    label="Nome"
                    required
            ></v-text-field>
            <v-text-field
                    v-model.trim="email"
                    :counter="50"
                    type="email"
                    :rules="emailRules"
                    label="E-mail"
                    required
            ></v-text-field>
            <v-text-field
                    v-model.trim="username"
                    :counter="35"
                    :rules="usernameRules"
                    label="Username"
                    required
            ></v-text-field>
            <v-text-field
                    v-model.trim="password"
                    :rules="passwordRules"
                    type="password"
                    label="Password"
                    required
            ></v-text-field>
            <v-select
                    v-model="tipo"
                    :items="items"
                    :rules="[v => !!v || 'Tipo is required']"
                    label="Tipo"
                    required
            ></v-select>
            <v-btn
                    :disabled="!isFormValid"
                    color="success"
                    class="mr-4"
                    @click="addSocio"
            >
                Adicionar
            </v-btn>

            <v-btn
                    color="error"
                    class="mr-4"
                    @click="reset"
            >
                Limpar
            </v-btn>
           <!-- <b-input v-model.trim="username" :state="isUsernameValid" required
                     placeholder="Enter your username"/>
            <b-input v-model="password" :state="isPasswordValid" required
                     placeholder="Enter your password"/>
            <b-input v-model.trim="name" :state="isNameValid" required
                     placeholder="Enter your name"/>
            <b-input ref="email" v-model.trim="email" type="email"
                     :state="isEmailValid" required pattern=".+@my.ipleiria.pt" placeholder="Enter
your e-mail"/>-->
         <!--   <b-select v-model="courseCode" :options="courses"
                      :state="isCourseValid" required value-field="code" text-field="name">
                <template v-slot:first>
                    <option :value="null" disabled>-- Please select the Course --
                    </option>
                </template>
            </b-select>-->
           <!-- <p class="text-danger" v-show="errorMsg">{{ errorMsg }}</p>
            <nuxt-link to="/students">Return</nuxt-link>
            <button type="reset" @click="reset">RESET</button>
            <button @click.prevent="create"
                    :disabled="!isFormValid">CREATE
            </button>-->
        </form>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                username: null,
                password: null,
                name: null,
                nameRules: [
                    v => !!v || 'Name is required',
                    v => (v && v.length <= 30) || 'Name must be less than 30 characters',
                ],
                email: null,
                emailRules: [
                    v => !!v || 'E-mail is required',
                    v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
                ],
                tipo: null,
                items: [
                    'ADMINISTRADOR',
                    'TREINADOR',
                    'ATLETA',
                    'SOCIO SIMPLE',
                ],
                errorMsg: false,
                isFormValid: true,
            }
        },
        /*created() {
            this.$axios.$get('/api/courses').then(courses => {
                this.courses = courses
            })
        }, computed: {
            isUsernameValid() {
                if (!this.username) {
                    return null
                }
                let usernameLen = this.username.length
                if (usernameLen < 3 || usernameLen > 15) {
                    return false
                }
                return true
            },
            isPasswordValid() {
                if (!this.password) {
                    return null
                }
                let passwordLen = this.password.length
                if (passwordLen < 3 || passwordLen > 255) {
                    return false
                }
                return true
            },
            isNameValid() {
                if (!this.name) {
                    return null
                }
                let nameLen = this.name.length
                if (nameLen < 3 || nameLen > 25) {
                    return false
                }
                return true
            },
            isEmailValid() {
                if (!this.email) {
                    return null
                }

                return this.$refs.email.checkValidity()
            },
            isCourseValid() {
                if (!this.courseCode) {
                    return null
                }
                return this.courses.some(course => this.courseCode === course.code)
            },
            isFormValid() {
                if (!this.isUsernameValid) {
                    return false
                }
                if (!this.isPasswordValid) {
                    return false
                }
                if (!this.isNameValid) {
                    return false
                }
                if (!this.isEmailValid) {
                    return false
                }
                if (!this.isCourseValid) {
                    return false
                }
                return true
            }
        },*/
        methods: {
            reset () {
                this.$refs.form.reset()
            },
            create() {
                if (this.$refs.form.validate()) {
                    this.$axios.$post('/api/students', {
                        username: this.username,
                        password: this.password,
                        name: this.name,
                        email: this.email,
                        courseCode: this.courseCode
                    })
                        .then(() => {
                            this.$router.push('/students')
                        })
                        .catch(error => {
                            this.errorMsg = error.response.data
                        })
                }
            }
        }
    }
</script>

<style scoped>

</style>