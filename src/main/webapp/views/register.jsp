<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Your Magical Voyage</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .register-container {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
            padding: 40px;
            width: 100%;
            max-width: 500px;
        }

        .logo {
            text-align: center;
            margin-bottom: 30px;
        }

        .logo h1 {
            color: #333;
            font-size: 2.5rem;
            font-weight: 700;
            background: linear-gradient(135deg, #667eea, #764ba2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            margin-bottom: 5px;
        }

        .logo p {
            color: #666;
            font-size: 0.9rem;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
            font-size: 0.9rem;
        }

        .input-with-icon {
            position: relative;
        }

        .input-with-icon i {
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: #666;
            z-index: 1;
        }

        .input-with-icon input,
        .input-with-icon select {
            width: 100%;
            padding: 12px 15px 12px 45px;
            border: 2px solid #e1e5e9;
            border-radius: 10px;
            font-size: 1rem;
            transition: all 0.3s ease;
            background: #fff;
            font-family: 'Poppins', sans-serif;
        }

        .input-with-icon input:focus,
        .input-with-icon select:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }

        .btn-register {
            width: 100%;
            padding: 12px;
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-top: 10px;
        }

        .btn-register:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }

        .login-link {
            text-align: center;
            margin-top: 20px;
            color: #666;
        }

        .login-link a {
            color: #667eea;
            text-decoration: none;
            font-weight: 500;
        }

        .login-link a:hover {
            text-decoration: underline;
        }

        .alert {
            padding: 12px 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 0.9rem;
        }

        .alert-error {
            background: #fee;
            border: 1px solid #f5c6cb;
            color: #721c24;
        }

        .role-options {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 10px;
            margin-top: 10px;
        }

        .role-option {
            text-align: center;
            padding: 15px;
            border: 2px solid #e1e5e9;
            border-radius: 10px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .role-option:hover {
            border-color: #667eea;
        }

        .role-option.selected {
            border-color: #667eea;
            background: rgba(102, 126, 234, 0.1);
        }

        .role-option i {
            font-size: 1.5rem;
            color: #667eea;
            margin-bottom: 8px;
        }

        .role-option span {
            display: block;
            font-size: 0.8rem;
            color: #333;
            font-weight: 500;
        }

        .password-strength {
            margin-top: 5px;
            height: 4px;
            background: #e1e5e9;
            border-radius: 2px;
            overflow: hidden;
        }

        .strength-meter {
            height: 100%;
            width: 0%;
            transition: all 0.3s ease;
        }

        .strength-weak { background: #e74c3c; width: 33%; }
        .strength-medium { background: #f39c12; width: 66%; }
        .strength-strong { background: #27ae60; width: 100%; }

        @media (max-width: 480px) {
            .register-container {
                padding: 30px 20px;
            }

            .logo h1 {
                font-size: 2rem;
            }

            .form-row {
                grid-template-columns: 1fr;
            }

            .role-options {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="register-container">
        <div class="logo">
            <h1><i class="fas fa-magic"></i> Magical Voyage</h1>
            <p>Create your account</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <i class="fas fa-exclamation-circle"></i> ${error}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/auth/register" method="post">
            <div class="form-row">
                <div class="form-group">
                    <label for="name">Full Name</label>
                    <div class="input-with-icon">
                        <i class="fas fa-user"></i>
                        <input type="text" id="name" name="name" placeholder="Enter your full name" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="email">Email Address</label>
                    <div class="input-with-icon">
                        <i class="fas fa-envelope"></i>
                        <input type="email" id="email" name="email" placeholder="Enter your email" required>
                    </div>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="password">Password</label>
                    <div class="input-with-icon">
                        <i class="fas fa-lock"></i>
                        <input type="password" id="password" name="password" placeholder="Create a password" required>
                    </div>
                    <div class="password-strength">
                        <div class="strength-meter" id="passwordStrength"></div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <div class="input-with-icon">
                        <i class="fas fa-phone"></i>
                        <input type="tel" id="phone" name="phone" placeholder="Enter your phone">
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label>Select Role</label>
                <div class="role-options">
                    <div class="role-option" data-role="TRAVELER">
                        <i class="fas fa-user"></i>
                        <span>Traveler</span>
                    </div>
                    <div class="role-option" data-role="TRAVEL_AGENT">
                        <i class="fas fa-briefcase"></i>
                        <span>Travel Agent</span>
                    </div>
                    <div class="role-option" data-role="ADMIN">
                        <i class="fas fa-cog"></i>
                        <span>Admin</span>
                    </div>
                </div>
                <input type="hidden" id="role" name="role" value="TRAVELER" required>
            </div>

            <button type="submit" class="btn-register">
                <i class="fas fa-user-plus"></i> Create Account
            </button>
        </form>

        <div class="login-link">
            Already have an account? <a href="${pageContext.request.contextPath}/views/login.jsp">Sign in here</a>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const roleOptions = document.querySelectorAll('.role-option');
            const roleInput = document.getElementById('role');
            const passwordInput = document.getElementById('password');
            const strengthMeter = document.getElementById('passwordStrength');

            roleOptions.forEach(option => {
                option.addEventListener('click', function() {
                    roleOptions.forEach(opt => opt.classList.remove('selected'));
                    this.classList.add('selected');
                    roleInput.value = this.getAttribute('data-role');
                });
            });

            passwordInput.addEventListener('input', function() {
                const password = this.value;
                let strength = 0;

                if (password.length >= 8) strength++;
                if (password.match(/[a-z]/) && password.match(/[A-Z]/)) strength++;
                if (password.match(/\d/)) strength++;
                if (password.match(/[^a-zA-Z\d]/)) strength++;

                strengthMeter.className = 'strength-meter';
                if (strength <= 1) {
                    strengthMeter.classList.add('strength-weak');
                } else if (strength <= 3) {
                    strengthMeter.classList.add('strength-medium');
                } else {
                    strengthMeter.classList.add('strength-strong');
                }
            });

            document.querySelector('.role-option[data-role="TRAVELER"]').classList.add('selected');
        });
    </script>
</body>
</html>