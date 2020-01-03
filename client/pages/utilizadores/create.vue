<template>
    <div>
        <v-btn class="ma-2" color="blue darken-2" to="/utilizadores" dark>
            <v-icon dark left>mdi-arrow-left</v-icon>Back
        </v-btn>
        <h1>Create a new Socio</h1>
        <form ref="form" @submit.prevent="create" :disabled="!isFormValid" lazy-validation>
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
                    ref="email"
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
                    @click="create"
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
            <b-input ref="username" v-model.trim="username" type="username"
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
                    v => (v && v.length <= 30 && v.length >= 3) || 'Name must be less than 30 characters and more than 3',
                ],
                username: null,
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
                usernameRules: [
                    v => !!v || 'UserName is required',
                    v => (v && v.length <= 35 && v.length >= 4) || 'UserName must be less than 35 characters and more than 4',
                ],
                passwordRules: [
                    v => !!v || 'Password is required',
                    v => (v && v.length <= 20 && v.length >= 6) || 'Password must be less than 20 characters and more than 6 characters',
                ],
                errorMsg: false
            }
        },
        /*created() {
            this.$axios.$get('/api/courses').then(courses => {
                this.courses = courses
            })
        }, */
        computed: {
        isUsernameValid() {
            if (!this.username) {
                return null
            }
            let usernameLen = this.username.length
            if (usernameLen < 4 || usernameLen > 35) {
                return false
            }
            return true
        },
        isPasswordValid() {
            if (!this.password) {
                return null
            }
            let passwordLen = this.password.length
            if (passwordLen < 6 || passwordLen > 20) {
                return false
            }
            return true
        },
        isNameValid() {
            if (!this.name) {
                return null
            }
            let nameLen = this.name.length
            if (nameLen < 3 || nameLen > 30) {
                return false
            }
            return true
        },
        isEmailValid() {
            if (!this.username) {
                return null;
            }

            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(this.username);
        },
        isTypeValid() {
            if (!this.tipo) {
                return false
            }
            //return true;
            return this.items.includes(this.tipo);
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
            if (!this.isTypeValid) {
                return false
            }
            return true
        }
    },
        methods: {
            reset () {
                this.$refs.form.reset()
            },
            create() {
                    switch (this.tipo) {
                        case 'ADMINISTRADOR':
                            this.$axios.$post('/api/users/administradores', {
                                username: this.username,
                                password: this.password,
                                name: this.name,
                                username: this.username,
                            })
                                .then(() => {
                                    this.$router.push('/utilizadores')
                                })
                                .catch(error => {
                                    this.errorMsg = error.response.data
                                })

                            break;
                        case 'TREINADOR':
                            this.$axios.$post('/api/users/treinadores', {
                                username: this.username,
                                password: this.password,
                                name: this.name,
                                username: this.username,
                            })
                                .then(() => {
                                    this.$router.push('/utilizadores')
                                })
                                .catch(error => {
                                    this.errorMsg = error.response.data
                                })
                                break;
                        case 'ATLETA':
                            this.$axios.$post('/api/users/atletas', {
                                username: this.username,
                                password: this.password,
                                name: this.name,
                                username: this.username,
                            })
                                .then(() => {
                                    this.$router.push('/utilizadores')
                                })
                                .catch(error => {
                                    this.errorMsg = error.response.data
                                })
                            break;
                        case 'SOCIO SIMPLE':
                            this.$axios.$post('/api/users', {
                                username: this.username,
                                password: this.password,
                                name: this.name,
                                username: this.username,
                            })
                                .then(() => {
                                    this.$router.push('/utilizadores')
                                })
                                .catch(error => {
                                    this.errorMsg = error.response.data
                                })
                            break;
                        default:
                            this.errorMsg = "Error : Type undefined";
                            break;
                    }
            }
        }
    }
</script>

<style scoped>

</style>