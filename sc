



if game.PlaceId == 2788229376 then
	local L_57_ = game:GetService('UserInputService')
	local function L_58_func()
		if not shared.Global.Inventory.Enabled then
			return
		end
		local L_114_ = game.Players.LocalPlayer
		local L_115_ = L_114_:FindFirstChildOfClass("Backpack")
		if not L_115_ then
			return
		end
		local L_116_ = {}
		for L_118_forvar0, L_119_forvar1 in pairs(L_115_:GetChildren()) do
			table.insert(L_116_, L_119_forvar1)
		end
		for L_120_forvar0, L_121_forvar1 in pairs(L_115_:GetChildren()) do
			L_121_forvar1.Parent = nil
		end
		local function L_117_func(L_122_arg0)
			return shared.Global.Inventory.Order[L_122_arg0.Name] or 999
		end
		table.sort(L_116_, function(L_123_arg0, L_124_arg1)
			local L_125_ = L_117_func(L_123_arg0)
			local L_126_ = L_117_func(L_124_arg1)
			return L_125_ < L_126_
		end)
		for L_127_forvar0, L_128_forvar1 in ipairs(L_116_) do
			local L_129_ = L_117_func(L_128_forvar1)
			local L_130_ = math.min(L_129_, 10)
			L_128_forvar1.Parent = L_115_
		end
	end
	L_57_.InputBegan:Connect(function(L_131_arg0, L_132_arg1)
		if L_132_arg1 then
			return
		end
		if L_131_arg0.KeyCode.Name == shared.Global.Keys.Inventory then
			L_58_func()
		end
	end)
	local L_59_ = game:GetService("Players").LocalPlayer
	local L_60_ = false
	L_59_:GetMouse().KeyDown:Connect(function(L_133_arg0)
		if shared.Global.Macro.Settings.Enabled then
			local L_134_ = shared.Global.Keys.Macro
			if L_133_arg0 == L_134_:lower() then
				if shared.Global.Macro.Settings.Mode == 'toggle' then
					shared.Global.Macro.Configurations.Enabled = not shared.Global.Macro.Configurations.Enabled
				elseif shared.Global.Macro.Settings.Mode == 'hold' then
					L_60_ = true
					shared.Global.Macro.Configurations.Enabled = true
				end
				if shared.Global.Macro.Configurations.Enabled then
					repeat
						game:GetService("RunService").Heartbeat:wait()
						game:GetService("VirtualInputManager"):SendMouseWheelEvent("0", "0", true, game)
						game:GetService("RunService").Heartbeat:wait()
						game:GetService("VirtualInputManager"):SendMouseWheelEvent("0", "0", false, game)
						game:GetService("RunService").Heartbeat:wait()
					until not shared.Global.Macro.Configurations.Enabled
				end
			end
		end
	end)
	L_59_:GetMouse().KeyUp:Connect(function(L_135_arg0)
		if shared.Global.Macro.Settings.Mode == 'hold' and L_135_arg0 == shared.Global.Keys.Macro:lower() then
			L_60_ = false
			shared.Global.Macro.Configurations.Enabled = false
		end
	end)
	local L_61_ = game:GetService("Players")
	local L_62_ = L_61_.LocalPlayer
	local L_63_ = game:GetService("Workspace").CurrentCamera
	local L_64_ = game:GetService("UserInputService")
	local L_65_ = game:GetService("RunService")
	local L_66_ = game:GetService("VirtualInputManager")
	local L_67_ = 0
	local L_68_ = false
	local L_69_ = nil
	local L_70_ = shared.Global.Trigger
	local L_71_ = L_70_.Start
	local L_72_ = L_70_.End
	local L_73_ = shared.Global.Keys.Trigger
	local L_74_ = L_70_.Enabled
	local L_75_ = L_70_.Mode
	if L_71_ >= 0.06 then
		L_71_ = 0.001
	elseif L_71_ < 0.06 then
		if L_71_ < 0.0001 then
			L_71_ = 0.0001
		end
	end
	local L_76_ = 0.9
	local L_77_ = {
		"Head",
		"UpperTorso",
		"LowerTorso",
		"HumanoidRootPart",
		"LeftHand",
		"RightHand",
		"LeftLowerArm",
		"RightLowerArm",
		"LeftUpperArm",
		"RightUpperArm",
		"LeftFoot",
		"LeftLowerLeg",
		"LeftUpperLeg",
		"RightLowerLeg",
		"RightUpperLeg",
		"RightFoot"
	}
	local function L_78_func()
		local L_136_ = L_62_.Character:FindFirstChildOfClass("Tool")
		if L_136_ then
			local L_137_ = L_136_.Name:lower()
			local L_138_ = {
				"knife",
				"[knife]",
				"katana",
				"[katana]",
				"[phone]",
				"[wallet]",
				"tipjar",
				"[LockPicker]",
				"[Hamburger]",
				"[Pizza]",
				"[Cranberry]",
				"[Chicken]",
				"[Taco]",
				"[HotDog]",
				"[Donut]",
				"[Meat]"
			}
			return table.find(L_138_, L_137_) ~= nil
		end
		return false
	end
	local function L_79_func(L_139_arg0)
		return L_139_arg0 and L_139_arg0.Character and L_139_arg0.Character:FindFirstChild("BodyEffects") and L_139_arg0.Character.BodyEffects["K.O"].Value == true
	end
	local function L_80_func(L_140_arg0)
		return L_140_arg0 and L_140_arg0.Character and L_140_arg0.Character:FindFirstChild("GRABBING_CONSTRAINT") ~= nil
	end
	local function L_81_func(L_141_arg0, L_142_arg1)
		L_66_:SendMouseButtonEvent(L_141_arg0, L_142_arg1, 0, true, game, false)
		L_66_:SendMouseButtonEvent(L_141_arg0, L_142_arg1, 0, false, game, false)
	end
	local function L_82_func()
		local L_143_ = L_64_:GetMouseLocation()
		return L_143_.X, L_143_.Y
	end
	local function L_83_func(L_144_arg0)
		local L_145_ = L_63_:WorldToViewportPoint(L_144_arg0)
		local L_146_ = Vector2.new(L_63_.ViewportSize.X / 2, L_63_.ViewportSize.Y / 2)
		return (Vector2.new(L_145_.X, L_145_.Y) - L_146_).Magnitude <= L_63_.ViewportSize.X / 2
	end
	local function L_84_func(L_147_arg0)
		local L_148_ = L_62_:GetMouse()
		return L_148_.Target and L_148_.Target:IsDescendantOf(L_147_arg0.Character)
	end
	local function L_85_func(L_149_arg0)
		local L_150_ = L_149_arg0.Character:FindFirstChild("HumanoidRootPart")
		if L_150_ then
			local L_151_ = L_150_.AssemblyLinearVelocity
			return L_151_
		end
		return Vector3.zero
	end
	local function L_86_func(L_152_arg0, L_153_arg1)
		local L_154_ = L_152_arg0.Character:FindFirstChild("HumanoidRootPart")
		if not L_154_ then
			return L_152_arg0.Character.HumanoidRootPart.Position
		end
		local L_155_ = L_85_func(L_152_arg0)
		local L_156_ = L_154_.Position + L_155_ * L_153_arg1
		return L_156_
	end
	local function L_87_func(L_157_arg0)
		return L_76_
	end
	local function L_88_func(L_158_arg0)
		for L_159_forvar0, L_160_forvar1 in pairs(L_77_) do
			local L_161_ = L_158_arg0.Character:FindFirstChild(L_160_forvar1)
			if L_161_ and L_161_:IsDescendantOf(L_158_arg0.Character) then
				local L_162_ = L_161_.Position
				local L_163_ = (L_162_ - L_63_.CFrame.Position).Magnitude
				local L_164_ = L_87_func(L_163_)
				local L_165_ = L_86_func(L_158_arg0, 0.1)
				local L_166_, L_167_ = L_63_:WorldToViewportPoint(L_165_)
				if L_167_ and L_83_func(L_165_) then
					local L_168_ = Vector2.new(L_166_.X, L_166_.Y)
					local L_169_ = Vector2.new(
                            math.random(- L_164_ * 2, L_164_ * 2), math.random(- L_164_ * 2, L_164_ * 2))
					local L_170_ = L_168_ + L_169_
					local L_171_, L_172_ = L_82_func()
					local L_173_ = 0.1
					local L_174_ = L_171_ + (L_170_.X - L_171_) * L_173_
					local L_175_ = L_172_ + (L_170_.Y - L_172_) * L_173_
					L_174_ = math.clamp(L_174_, 0, L_63_.ViewportSize.X)
					L_175_ = math.clamp(L_175_, 0, L_63_.ViewportSize.Y)
        
                        -- New logic to use start and end delay
					local L_176_ = os.clock()
					local L_177_ = math.random() * (L_72_ - L_71_) + L_71_
					if L_176_ - L_67_ >= L_177_ and not L_78_func() then
						L_67_ = L_176_
						local L_178_, L_179_ = L_82_func()
						L_81_func(L_178_, L_179_)
					end
				end
			end
		end
	end
	local function L_89_func()
		if L_69_ and L_69_.Character then
			local L_180_ = L_69_.Character:FindFirstChild("Humanoid")
			if L_180_ and L_180_.Health > 0 and not L_79_func(L_69_) and not L_80_func(L_69_) then
				if L_84_func(L_69_) then
					L_88_func(L_69_)
				end
			else
				L_69_ = nil
			end
		end
	end
	L_64_.InputBegan:Connect(function(L_181_arg0, L_182_arg1)
		if not L_182_arg1 and L_181_arg0.KeyCode.Name == L_73_ then
			if L_75_ == "toggle" then
				L_68_ = not L_68_
			elseif L_75_ == "hold" then
				L_68_ = true
			end
		end
	end)
	L_64_.InputEnded:Connect(function(L_183_arg0, L_184_arg1)
		if not L_184_arg1 and L_183_arg0.KeyCode.Name == L_73_ and L_75_ == "hold" then
			L_68_ = false
		end
	end)
	L_65_.RenderStepped:Connect(function()
		if L_74_ and L_68_ then
			L_89_func()
		end
	end)
	local L_90_ = game:GetService("Players")
	local L_91_ = L_90_.LocalPlayer
	local L_92_ = L_91_:GetMouse()
	local L_93_ = game:GetService("RunService")
	local L_94_ = game.Workspace.CurrentCamera
	local L_95_ = shared.Global.Camera.FOV
	local L_96_ = false
	local L_97_ = shared.Global.Core.Checks.Whitelist
	local L_98_ = 5
	local L_99_ = 10
	local L_100_ = 0.03
	local L_101_ = shared.Global.Camera.Distance or 30
	local function L_102_func()
		local L_185_ = L_91_.Character and L_91_.Character:FindFirstChild("HumanoidRootPart")
		if L_185_ then
			local L_186_ = L_94_.CFrame.Position
			local L_187_ = (L_186_ - L_185_.Position).Magnitude
			return L_187_ < L_98_
		end
		return false
	end
	local function L_103_func()
		local L_188_ = L_91_.Character and L_91_.Character:FindFirstChild("HumanoidRootPart")
		if L_188_ then
			local L_189_ = L_94_.CFrame.Position
			local L_190_ = (L_189_ - L_188_.Position).Magnitude
			return L_190_ > L_99_
		end
		return false
	end
	local function L_104_func()
	end
	L_93_.RenderStepped:Connect(L_104_func)
	local function L_105_func()
		local L_191_, L_192_ = nil, math.huge
		local L_193_ = shared.Global.Core.Enabled
		local L_194_ = shared.Global.Core.Checks.Whitelist
		for L_195_forvar0, L_196_forvar1 in pairs(L_90_:GetPlayers()) do
			if L_196_forvar1 ~= L_91_ and L_196_forvar1.Character and L_196_forvar1.Character:FindFirstChild("HumanoidRootPart") then
				if L_193_ and table.find(L_194_, L_196_forvar1.Name) then
					break
				end
				local L_197_, L_198_ = L_94_:WorldToScreenPoint(L_196_forvar1.Character.HumanoidRootPart.Position)
				local L_199_ = (Vector2.new(L_197_.X, L_197_.Y) - Vector2.new(L_92_.X, L_92_.Y)).Magnitude
				L_199_ = L_199_ * L_100_
				if L_199_ < L_192_ and L_198_ then
					L_192_ = L_199_
					L_191_ = L_196_forvar1
				end
			end
		end
		return L_191_
	end
	local function L_106_func(L_200_arg0)
		local L_201_ = math.huge
		local L_202_ = nil
		if L_200_arg0 and L_200_arg0:IsDescendantOf(game.Workspace) then
			for L_203_forvar0, L_204_forvar1 in ipairs(L_200_arg0:GetChildren()) do
				if L_204_forvar1:IsA("BasePart") then
					local L_205_, L_206_ = L_94_:WorldToScreenPoint(L_204_forvar1.Position)
					if L_206_ then
						local L_207_ = (Vector2.new(L_205_.X, L_205_.Y) - Vector2.new(L_92_.X, L_92_.Y)).Magnitude
						if L_207_ < L_201_ then
							L_201_ = L_207_
							L_202_ = L_204_forvar1
						end
					end
				end
			end
		end
		return L_202_
	end
	local function L_107_func(L_208_arg0)
		local L_209_ = L_91_.Character and L_91_.Character:FindFirstChild("HumanoidRootPart")
		if L_209_ and L_208_arg0 and L_208_arg0.Character then
			local L_210_ = L_208_arg0.Character.HumanoidRootPart.Position
			local L_211_ = (L_210_ - L_209_.Position).Magnitude
			return L_211_ <= L_101_
		end
		return false
	end
	local function L_108_func()
		return L_69_
	end
	L_92_.Button2Down:Connect(function()
		if shared.Global.Camera.MouseButton2 then
			L_96_ = true
		end
	end)
	L_92_.Button2Up:Connect(function()
		L_96_ = false
	end)
	local function L_109_func(L_212_arg0, L_213_arg1)
		if L_212_arg0 and L_213_arg1 then
			local L_214_ = Ray.new(L_94_.CFrame.Position, (L_213_arg1.Position - L_94_.CFrame.Position).unit * 500)
			local L_215_ = workspace:FindPartOnRay(L_214_, L_91_.Character)
			if L_215_ and L_215_.Parent ~= L_212_arg0.Character then
				return false
			end
		end
		return true
	end
	L_92_.KeyDown:Connect(function(L_216_arg0)
		local L_217_ = L_216_arg0:lower()
		if L_217_ == shared.Global.Keys.Target:lower() then
			if shared.Global.Camera.Enabled then
				if G_1_ then
					if L_69_ and L_69_.Character and L_69_.Character:FindFirstChildOfClass("Humanoid") then
						local L_218_ = L_69_.Character.Humanoid
						if L_218_.Health >= 1 and not L_79_func(L_69_) and not L_80_func(L_69_) then
							if L_107_func(L_69_) then
								if L_105_func() ~= L_69_ then
									local L_219_ = L_105_func()
									if L_219_ and L_219_.Character and L_219_.Character:FindFirstChildOfClass("Humanoid").Health >= 1 and not L_79_func(L_219_) and not L_80_func(L_219_) then
										L_69_ = L_219_
									end
								end
							else
								L_69_ = nil
								G_1_ = false
							end
						else
							L_69_ = nil
							G_1_ = false
						end
					end
				else
					local L_220_ = L_105_func()
					if L_220_ and L_220_.Character and L_220_.Character:FindFirstChildOfClass("Humanoid").Health >= 1 and not L_79_func(L_220_) and not L_80_func(L_220_) then
						if L_107_func(L_220_) then
							G_1_ = true
							L_69_ = L_220_
						end
					end
				end
			end
		end
		if L_217_ == shared.Global.Keys.Cancel:lower() then
			G_1_ = false
			L_69_ = nil
		end
	end)
	local function L_110_func(L_221_arg0)
		return L_221_arg0 and L_221_arg0.Character and L_221_arg0.Character:FindFirstChild("BodyEffects") and L_221_arg0.Character.BodyEffects["K.O"].Value == true
	end
	local function L_111_func(L_222_arg0)
		return L_222_arg0 and L_222_arg0.Character and L_222_arg0.Character:FindFirstChild("GRABBING_CONSTRAINT") ~= nil
	end
	local function L_112_func(L_223_arg0)
		if L_223_arg0 and L_223_arg0.Character then
			local L_224_ = L_223_arg0.Character.HumanoidRootPart.Position
			local L_225_ = L_94_.CFrame.Position
			local L_226_ = (L_224_ - L_225_).unit
			local L_227_ = L_94_.CFrame.LookVector.unit
			local L_228_ = (L_224_ - L_225_).Magnitude
			local L_229_ = 0.98
			if L_228_ > 50 then
				L_229_ = 0.95
			end
			return L_226_:Dot(L_227_) > L_229_
		end
		return false
	end
	local function L_113_func(L_230_arg0)
		local L_231_ = game:GetService("Players").LocalPlayer:GetMouse().Hit.p
		return (L_230_arg0.Position - L_231_).Magnitude
	end
	L_93_.RenderStepped:Connect(function()
		if G_1_ and L_69_ and L_69_.Character then
			local L_232_ = L_69_.Character:FindFirstChildOfClass("Humanoid")
			if not L_232_ or L_232_.Health < 1 or L_110_func(L_69_) or L_111_func(L_69_) then
				L_69_ = nil
				G_1_ = false
				return
			end
			if shared.Global.Camera.Enabled then
				if shared.Global.Camera.Configurations.Value == 0 then
					return
				end
				if shared.Global.Camera.MouseButton2 then
					if L_96_ then
						if shared.Global.Camera.Configurations.ThirdPerson == false then
							if L_102_func() then
								if L_112_func(L_69_) then
									local L_233_ = L_69_.Character:FindFirstChild("Head")
									local L_234_ = L_69_.Character:FindFirstChild("LowerTorso")
									local L_235_ = nil
									if L_233_ and L_234_ then
										local L_236_ = L_113_func(L_233_)
										local L_237_ = L_113_func(L_234_)
										if L_236_ < L_237_ then
											L_235_ = L_233_
										else
											L_235_ = L_234_
										end
									elseif L_233_ then
										L_235_ = L_233_
									elseif L_234_ then
										L_235_ = L_234_
									end
									if L_235_ and L_109_func(L_69_, L_235_) then
										local L_238_ = L_235_.Position
										local L_239_ = L_69_.Character.HumanoidRootPart.Position
										local L_240_ = (L_238_ - L_239_).Magnitude
										if L_240_ <= math.sqrt(L_95_ ^ 2 + L_95_ ^ 2 + L_95_ ^ 2) then
											local L_241_
											if shared.Global.Camera.Resolver then
												local L_242_ = L_69_.Character:FindFirstChildOfClass("Humanoid")
												if L_242_ then
													local L_243_ = L_242_.MoveDirection
													L_241_ = L_235_.Position + (L_243_ * Vector3.new(
                                                            shared.Global.Camera.Prediction.X, shared.Global.Camera.Prediction.Y, shared.Global.Camera.Prediction.Z))
												end
											else
												local L_244_ = L_69_.Character.HumanoidRootPart.Velocity
												L_241_ = L_235_.Position + (L_244_ * Vector3.new(
                                                        shared.Global.Camera.Prediction.X, shared.Global.Camera.Prediction.Y, shared.Global.Camera.Prediction.Z))
											end
											if shared.Global.Camera.Configurations.Value ~= 0 then
												if L_241_ then
													local L_245_ = L_94_.CFrame.Position
													local L_246_ = math.random(95, 105) / 100
													local L_247_ = shared.Global.Camera.Configurations.Value * L_246_
													local L_248_ = CFrame.new(L_245_, L_241_)
													L_94_.CFrame = L_94_.CFrame:Lerp(L_248_, L_247_)
												end
											end
										end
									end
								end
							end
						end
					end
				else
					if shared.Global.Camera.Configurations.ThirdPerson == false then
						if L_102_func() then
							if L_112_func(L_69_) then
								local L_249_ = L_69_.Character:FindFirstChild("Head")
								local L_250_ = L_69_.Character:FindFirstChild("LowerTorso")
								local L_251_ = nil
								if L_249_ and L_250_ then
									local L_252_ = L_113_func(L_249_)
									local L_253_ = L_113_func(L_250_)
									if L_252_ < L_253_ then
										L_251_ = L_249_
									else
										L_251_ = L_250_
									end
								elseif L_249_ then
									L_251_ = L_249_
								elseif L_250_ then
									L_251_ = L_250_
								end
								if L_251_ and L_109_func(L_69_, L_251_) then
									local L_254_ = L_251_.Position
									local L_255_ = L_69_.Character.HumanoidRootPart.Position
									local L_256_ = (L_254_ - L_255_).Magnitude
									if L_256_ <= math.sqrt(L_95_.X ^ 2 + L_95_.Y ^ 2 + L_95_.Z ^ 2) then
										local L_257_
										if shared.Global.Camera.Resolver then
											local L_258_ = L_69_.Character:FindFirstChildOfClass("Humanoid")
											if L_258_ then
												local L_259_ = L_258_.MoveDirection
												L_257_ = L_251_.Position + (L_259_ * Vector3.new(
                                                        shared.Global.Camera.Prediction.X, shared.Global.Camera.Prediction.Y, shared.Global.Camera.Prediction.Z))
											end
										else
											local L_260_ = L_69_.Character.HumanoidRootPart.Velocity
											L_257_ = L_251_.Position + (L_260_ * Vector3.new(
                                                    shared.Global.Camera.Prediction.X, shared.Global.Camera.Prediction.Y, shared.Global.Camera.Prediction.Z))
										end
										if shared.Global.Camera.Configurations.Value ~= 0 then
											if L_257_ then
												local L_261_ = L_94_.CFrame.Position
												local L_262_ = math.random(95, 105) / 100
												local L_263_ = (shared.Global.Camera.Configurations.Value * L_262_) * 0.1
												local L_264_ = CFrame.new(L_261_, L_257_)
												L_94_.CFrame = L_94_.CFrame:Lerp(L_264_, L_263_)
											end
										end
									end
								end
							end
						end
					end
				end
			end
		end
	end)
	L_64_.InputEnded:Connect(function(L_265_arg0, L_266_arg1)
		if L_265_arg0.KeyCode == Enum.KeyCode[shared.Global.Keys.Target:upper()] and shared.Global.Camera.Method == "hold" then  -- Updated to Keybind
			G_2_ = false
		end
	end)
else
	local L_267_ = game.Players.LocalPlayer
	local L_268_ = L_267_.PlayerGui
	local L_269_ = {
		15186202290,
		9825515356,
		84366677940861,
		77369032494150,
		132023669786646
	}
	local L_270_ = game.PlaceId
	local L_271_ = false
	local function L_272_func(L_274_arg0)
		for L_275_forvar0, L_276_forvar1 in ipairs(L_274_arg0:GetChildren()) do
			if L_276_forvar1.Name == "Settings" then
				return L_276_forvar1
			end
			local L_277_ = L_272_func(L_276_forvar1)
			if L_277_ then
				return L_277_
			end
		end
		return nil
	end
	local function L_273_func()
		local L_278_ = game:GetService('UserInputService')
		local function L_279_func()
			if not shared.Global.Inventory.Enabled then
				return
			end
			local L_378_ = game.Players.LocalPlayer
			local L_379_ = L_378_:FindFirstChildOfClass("Backpack")
			if not L_379_ then
				return
			end
			local L_380_ = {}
			for L_382_forvar0, L_383_forvar1 in pairs(L_379_:GetChildren()) do
				table.insert(L_380_, L_383_forvar1)
			end
			for L_384_forvar0, L_385_forvar1 in pairs(L_379_:GetChildren()) do
				L_385_forvar1.Parent = nil
			end
			local function L_381_func(L_386_arg0)
				return shared.Global.Inventory.Order[L_386_arg0.Name] or 999
			end
			table.sort(L_380_, function(L_387_arg0, L_388_arg1)
				local L_389_ = L_381_func(L_387_arg0)
				local L_390_ = L_381_func(L_388_arg1)
				return L_389_ < L_390_
			end)
			for L_391_forvar0, L_392_forvar1 in ipairs(L_380_) do
				local L_393_ = L_381_func(L_392_forvar1)
				local L_394_ = math.min(L_393_, 10)
				L_392_forvar1.Parent = L_379_
			end
		end
		L_278_.InputBegan:Connect(function(L_395_arg0, L_396_arg1)
			if L_396_arg1 then
				return
			end
			if L_395_arg0.KeyCode.Name == shared.Global.Keys.Inventory then
				L_279_func()
			end
		end)
		local L_280_ = game:GetService("Players").LocalPlayer
		local L_281_ = false
		L_280_:GetMouse().KeyDown:Connect(function(L_397_arg0)
			if shared.Global.Macro.Settings.Enabled then
				local L_398_ = shared.Global.Keys.Macro
				if L_397_arg0 == L_398_:lower() then
					if shared.Global.Macro.Settings.Mode == 'toggle' then
						shared.Global.Macro.Configurations.Enabled = not shared.Global.Macro.Configurations.Enabled
					elseif shared.Global.Macro.Settings.Mode == 'hold' then
						L_281_ = true
						shared.Global.Macro.Configurations.Enabled = true
					end
					if shared.Global.Macro.Configurations.Enabled then
						repeat
							game:GetService("RunService").Heartbeat:wait()
							game:GetService("VirtualInputManager"):SendMouseWheelEvent("0", "0", true, game)
							game:GetService("RunService").Heartbeat:wait()
							game:GetService("VirtualInputManager"):SendMouseWheelEvent("0", "0", false, game)
							game:GetService("RunService").Heartbeat:wait()
						until not shared.Global.Macro.Configurations.Enabled
					end
				end
			end
		end)
		L_280_:GetMouse().KeyUp:Connect(function(L_399_arg0)
			if shared.Global.Macro.Settings.Mode == 'hold' and L_399_arg0 == shared.Global.Keys.Macro:lower() then
				L_281_ = false
				shared.Global.Macro.Configurations.Enabled = false
			end
		end)
		local L_282_ = game:GetService("Players")
		local L_283_ = L_282_.LocalPlayer
		local L_284_ = game:GetService("Workspace").CurrentCamera
		local L_285_ = game:GetService("UserInputService")
		local L_286_ = game:GetService("RunService")
		local L_287_ = game:GetService("VirtualInputManager")
		local L_288_ = 0
		local L_289_ = false
		local L_290_ = nil
		local L_291_ = shared.Global.Trigger
		local L_292_ = L_291_.Start
		local L_293_ = L_291_.End
		local L_294_ = shared.Global.Keys.Trigger
		local L_295_ = L_291_.Enabled
		local L_296_ = L_291_.Mode
		if L_292_ >= 0.06 then
			L_292_ = 0.001
		elseif L_292_ < 0.06 then
			if L_292_ < 0.0001 then
				L_292_ = 0.0001
			end
		end
		local L_297_ = 0.9
		local L_298_ = {
			"Head",
			"UpperTorso",
			"LowerTorso",
			"HumanoidRootPart",
			"LeftHand",
			"RightHand",
			"LeftLowerArm",
			"RightLowerArm",
			"LeftUpperArm",
			"RightUpperArm",
			"LeftFoot",
			"LeftLowerLeg",
			"LeftUpperLeg",
			"RightLowerLeg",
			"RightUpperLeg",
			"RightFoot"
		}
                
                -- Prediction method integration
		local L_299_ = shared.Global['Silent'].Prediction
		local function L_300_func(L_400_arg0, L_401_arg1)
			if L_400_arg0 and L_400_arg0.Character then
				local L_402_ = L_400_arg0.Character[L_401_arg1].Velocity
				local L_403_ = shared.Global['Silent'].Prediction.X
				local L_404_ = shared.Global['Silent'].Prediction.Y
				local L_405_ = shared.Global['Silent'].Prediction.Z
				local L_406_ = Vector3.new(
                            L_402_.X * L_403_, L_402_.Y * L_404_, L_402_.Z * L_405_)
				if L_406_.Y < -30 then
					shared.Global['Silent'].Prediction = {
						X = 0,
						Y = 0,
						Z = 0
					}
					return L_406_
				elseif L_406_.Magnitude > 50 then
					return L_400_arg0.Character:FindFirstChild("Humanoid").MoveDirection * 16 * L_403_
				else
					shared.Global['Silent'].Prediction = L_299_
					return L_406_
				end
			end
			return Vector3.new(0, 0, 0)
		end
		local function L_301_func(L_407_arg0, L_408_arg1)
			local L_409_ = L_407_arg0.Character:FindFirstChild("HumanoidRootPart")
			if not L_409_ then
				return L_407_arg0.Character.HumanoidRootPart.Position
			end
			local L_410_ = L_300_func(L_407_arg0, "HumanoidRootPart")
			local L_411_ = L_409_.Position + L_410_ * L_408_arg1
			return L_411_
		end
		local function L_302_func()
			local L_412_ = L_283_.Character:FindFirstChildOfClass("Tool")
			if L_412_ then
				local L_413_ = L_412_.Name:lower()
				local L_414_ = {
					"knife",
					"[knife]",
					"katana",
					"[katana]",
					"[phone]",
					"[wallet]",
					"tipjar",
					"[LockPicker]",
					"[Hamburger]",
					"[Pizza]",
					"[Cranberry]",
					"[Chicken]",
					"[Taco]",
					"[HotDog]",
					"[Donut]",
					"[Meat]"
				}
				return table.find(L_414_, L_413_) ~= nil
			end
			return false
		end
		local function L_303_func(L_415_arg0)
			return L_415_arg0 and L_415_arg0.Character and L_415_arg0.Character:FindFirstChild("BodyEffects") and L_415_arg0.Character.BodyEffects["K.O"].Value == true
		end
		local function L_304_func(L_416_arg0)
			return L_416_arg0 and L_416_arg0.Character and L_416_arg0.Character:FindFirstChild("GRABBING_CONSTRAINT") ~= nil
		end
		local function L_305_func(L_417_arg0, L_418_arg1)
			L_287_:SendMouseButtonEvent(L_417_arg0, L_418_arg1, 0, true, game, false)
			L_287_:SendMouseButtonEvent(L_417_arg0, L_418_arg1, 0, false, game, false)
		end
		local function L_306_func()
			local L_419_ = L_285_:GetMouseLocation()
			return L_419_.X, L_419_.Y
		end
		local function L_307_func(L_420_arg0)
			local L_421_ = L_284_:WorldToViewportPoint(L_420_arg0)
			local L_422_ = Vector2.new(L_284_.ViewportSize.X / 2, L_284_.ViewportSize.Y / 2)
			return (Vector2.new(L_421_.X, L_421_.Y) - L_422_).Magnitude <= L_284_.ViewportSize.X / 2
		end
		local function L_308_func(L_423_arg0)
			local L_424_ = L_283_:GetMouse()
			return L_424_.Target and L_424_.Target:IsDescendantOf(L_423_arg0.Character)
		end
		local function L_309_func(L_425_arg0)
			return L_297_
		end
		local function L_310_func(L_426_arg0)
			for L_427_forvar0, L_428_forvar1 in pairs(L_298_) do
				local L_429_ = L_426_arg0.Character:FindFirstChild(L_428_forvar1)
				if L_429_ and L_429_:IsDescendantOf(L_426_arg0.Character) then
					local L_430_ = L_429_.Position
					local L_431_ = (L_430_ - L_284_.CFrame.Position).Magnitude
					local L_432_ = L_309_func(L_431_)
					local L_433_ = L_301_func(L_426_arg0, 0.1)
					local L_434_, L_435_ = L_284_:WorldToViewportPoint(L_433_)
					if L_435_ and L_307_func(L_433_) then
						local L_436_ = Vector2.new(L_434_.X, L_434_.Y)
						local L_437_ = Vector2.new(
                                    math.random(- L_432_ * 2, L_432_ * 2), math.random(- L_432_ * 2, L_432_ * 2))
						local L_438_ = L_436_ + L_437_
						local L_439_, L_440_ = L_306_func()
						local L_441_ = 0.1
						local L_442_ = L_439_ + (L_438_.X - L_439_) * L_441_
						local L_443_ = L_440_ + (L_438_.Y - L_440_) * L_441_
						L_442_ = math.clamp(L_442_, 0, L_284_.ViewportSize.X)
						L_443_ = math.clamp(L_443_, 0, L_284_.ViewportSize.Y)
                
                                -- New logic to use start and end delay
						local L_444_ = os.clock()
						local L_445_ = math.random() * (L_293_ - L_292_) + L_292_
						if L_444_ - L_288_ >= L_445_ and not L_302_func() then
							L_288_ = L_444_
							local L_446_, L_447_ = L_306_func()
							L_305_func(L_446_, L_447_)
						end
					end
				end
			end
		end
		local function L_311_func()
			if L_290_ and L_290_.Character then
				local L_448_ = L_290_.Character:FindFirstChild("Humanoid")
				if L_448_ and L_448_.Health > 0 and not L_303_func(L_290_) and not L_304_func(L_290_) then
					if L_308_func(L_290_) then
						L_310_func(L_290_)
					end
				else
					L_290_ = nil
				end
			end
		end
		L_285_.InputBegan:Connect(function(L_449_arg0, L_450_arg1)
			if not L_450_arg1 and L_449_arg0.KeyCode.Name == L_294_ then
				if L_296_ == "toggle" then
					L_289_ = not L_289_
				elseif L_296_ == "hold" then
					L_289_ = true
				end
			end
		end)
		L_285_.InputEnded:Connect(function(L_451_arg0, L_452_arg1)
			if not L_452_arg1 and L_451_arg0.KeyCode.Name == L_294_ and L_296_ == "hold" then
				L_289_ = false
			end
		end)
		L_286_.RenderStepped:Connect(function()
			if L_295_ and L_289_ then
				L_311_func()
			end
		end)
		local L_312_ = game:GetService("Players")
		local L_313_ = L_312_.LocalPlayer
		local L_314_ = L_313_:GetMouse()
		local L_315_ = game:GetService("RunService")
		local L_316_ = game.Workspace.CurrentCamera
		local L_317_ = shared.Global.Camera.FOV
		local L_318_ = false
		local L_319_ = shared.Global.Core.Checks.Whitelist
		local L_320_ = 5
		local L_321_ = 10
		local L_322_ = 0.03
		local L_323_ = shared.Global.Camera.Distance or 30
		local function L_324_func()
			local L_453_ = L_313_.Character and L_313_.Character:FindFirstChild("HumanoidRootPart")
			if L_453_ then
				local L_454_ = L_316_.CFrame.Position
				local L_455_ = (L_454_ - L_453_.Position).Magnitude
				return L_455_ < L_320_
			end
			return false
		end
		local function L_325_func()
			local L_456_ = L_313_.Character and L_313_.Character:FindFirstChild("HumanoidRootPart")
			if L_456_ then
				local L_457_ = L_316_.CFrame.Position
				local L_458_ = (L_457_ - L_456_.Position).Magnitude
				return L_458_ > L_321_
			end
			return false
		end
		local function L_326_func()
		end
		L_315_.RenderStepped:Connect(L_326_func)
		local function L_327_func()
			local L_459_, L_460_ = nil, math.huge
			local L_461_ = shared.Global.Core.Enabled
			local L_462_ = shared.Global.Core.Checks.Whitelist
			for L_463_forvar0, L_464_forvar1 in pairs(L_312_:GetPlayers()) do
				if L_464_forvar1 ~= L_313_ and L_464_forvar1.Character and L_464_forvar1.Character:FindFirstChild("HumanoidRootPart") then
					if L_461_ and table.find(L_462_, L_464_forvar1.Name) then
						break
					end
					local L_465_, L_466_ = L_316_:WorldToScreenPoint(L_464_forvar1.Character.HumanoidRootPart.Position)
					local L_467_ = (Vector2.new(L_465_.X, L_465_.Y) - Vector2.new(L_314_.X, L_314_.Y)).Magnitude
					L_467_ = L_467_ * L_322_
					if L_467_ < L_460_ and L_466_ then
						L_460_ = L_467_
						L_459_ = L_464_forvar1
					end
				end
			end
			return L_459_
		end
		local function L_328_func(L_468_arg0)
			local L_469_ = math.huge
			local L_470_ = nil
			if L_468_arg0 and L_468_arg0:IsDescendantOf(game.Workspace) then
				for L_471_forvar0, L_472_forvar1 in ipairs(L_468_arg0:GetChildren()) do
					if L_472_forvar1:IsA("BasePart") then
						local L_473_, L_474_ = L_316_:WorldToScreenPoint(L_472_forvar1.Position)
						if L_474_ then
							local L_475_ = (Vector2.new(L_473_.X, L_473_.Y) - Vector2.new(L_314_.X, L_314_.Y)).Magnitude
							if L_475_ < L_469_ then
								L_469_ = L_475_
								L_470_ = L_472_forvar1
							end
						end
					end
				end
			end
			return L_470_
		end
		local function L_329_func(L_476_arg0)
			local L_477_ = L_313_.Character and L_313_.Character:FindFirstChild("HumanoidRootPart")
			if L_477_ and L_476_arg0 and L_476_arg0.Character then
				local L_478_ = L_476_arg0.Character.HumanoidRootPart.Position
				local L_479_ = (L_478_ - L_477_.Position).Magnitude
				return L_479_ <= L_323_
			end
			return false
		end
		local function L_330_func()
			return L_290_
		end
		L_314_.Button2Down:Connect(function()
			if shared.Global.Camera.MouseButton2 then
				L_318_ = true
			end
		end)
		L_314_.Button2Up:Connect(function()
			L_318_ = false
		end)
		local function L_331_func(L_480_arg0, L_481_arg1)
			if L_480_arg0 and L_481_arg1 then
				local L_482_ = Ray.new(L_316_.CFrame.Position, (L_481_arg1.Position - L_316_.CFrame.Position).unit * 500)
				local L_483_ = workspace:FindPartOnRay(L_482_, L_313_.Character)
				if L_483_ and L_483_.Parent ~= L_480_arg0.Character then
					return false
				end
			end
			return true
		end
		L_314_.KeyDown:Connect(function(L_484_arg0)
			local L_485_ = L_484_arg0:lower()
			if L_485_ == shared.Global.Keys.Target:lower() then
				if shared.Global.Camera.Enabled then
					if G_1_ then
						if L_290_ and L_290_.Character and L_290_.Character:FindFirstChildOfClass("Humanoid") then
							local L_486_ = L_290_.Character.Humanoid
							if L_486_.Health >= 1 and not L_303_func(L_290_) and not L_304_func(L_290_) then
								if L_329_func(L_290_) then
									if L_327_func() ~= L_290_ then
										local L_487_ = L_327_func()
										if L_487_ and L_487_.Character and L_487_.Character:FindFirstChildOfClass("Humanoid").Health >= 1 and not L_303_func(L_487_) and not L_304_func(L_487_) then
											L_290_ = L_487_
										end
									end
								else
									L_290_ = nil
									G_1_ = false
								end
							else
								L_290_ = nil
								G_1_ = false
							end
						end
					else
						local L_488_ = L_327_func()
						if L_488_ and L_488_.Character and L_488_.Character:FindFirstChildOfClass("Humanoid").Health >= 1 and not L_303_func(L_488_) and not L_304_func(L_488_) then
							if L_329_func(L_488_) then
								G_1_ = true
								L_290_ = L_488_
							end
						end
					end
				end
			end
			if L_485_ == shared.Global.Keys.Cancel:lower() then
				G_1_ = false
				L_290_ = nil
			end
		end)
		local function L_332_func(L_489_arg0)
			return L_489_arg0 and L_489_arg0.Character and L_489_arg0.Character:FindFirstChild("BodyEffects") and L_489_arg0.Character.BodyEffects["K.O"].Value == true
		end
		local function L_333_func(L_490_arg0)
			return L_490_arg0 and L_490_arg0.Character and L_490_arg0.Character:FindFirstChild("GRABBING_CONSTRAINT") ~= nil
		end
		local function L_334_func(L_491_arg0)
			if L_491_arg0 and L_491_arg0.Character then
				local L_492_ = L_491_arg0.Character.HumanoidRootPart.Position
				local L_493_ = L_316_.CFrame.Position
				local L_494_ = (L_492_ - L_493_).unit
				local L_495_ = L_316_.CFrame.LookVector.unit
				local L_496_ = (L_492_ - L_493_).Magnitude
				local L_497_ = 0.98
				if L_496_ > 50 then
					L_497_ = 0.95
				end
				return L_494_:Dot(L_495_) > L_497_
			end
			return false
		end
		local function L_335_func(L_498_arg0)
			local L_499_ = game:GetService("Players").LocalPlayer:GetMouse().Hit.p
			return (L_498_arg0.Position - L_499_).Magnitude
		end
		L_315_.RenderStepped:Connect(function()
			if G_1_ and L_290_ and L_290_.Character then
				local L_500_ = L_290_.Character:FindFirstChildOfClass("Humanoid")
				if not L_500_ or L_500_.Health < 1 or L_332_func(L_290_) or L_333_func(L_290_) then
					L_290_ = nil
					G_1_ = false
					return
				end
				if shared.Global.Camera.Enabled then
					if shared.Global.Camera.Configurations.Value == 0 then
						return
					end
					if shared.Global.Camera.MouseButton2 then
						if L_318_ then
							if shared.Global.Camera.Configurations.ThirdPerson == false then
								if L_324_func() then
									if L_334_func(L_290_) then
										local L_501_ = L_290_.Character:FindFirstChild("Head")
										local L_502_ = L_290_.Character:FindFirstChild("LowerTorso")
										local L_503_ = nil
										if L_501_ and L_502_ then
											local L_504_ = L_335_func(L_501_)
											local L_505_ = L_335_func(L_502_)
											if L_504_ < L_505_ then
												L_503_ = L_501_
											else
												L_503_ = L_502_
											end
										elseif L_501_ then
											L_503_ = L_501_
										elseif L_502_ then
											L_503_ = L_502_
										end
										if L_503_ and L_331_func(L_290_, L_503_) then
											local L_506_ = L_503_.Position
											local L_507_ = L_290_.Character.HumanoidRootPart.Position
											local L_508_ = (L_506_ - L_507_).Magnitude
											if L_508_ <= math.sqrt(L_317_ ^ 2 + L_317_ ^ 2 + L_317_ ^ 2) then
												local L_509_
												if shared.Global.Camera.Resolver then
													local L_510_ = L_290_.Character:FindFirstChildOfClass("Humanoid")
													if L_510_ then
														local L_511_ = L_510_.MoveDirection
														L_509_ = L_503_.Position + (L_511_ * Vector3.new(
                                                                    shared.Global.Camera.Prediction.X, shared.Global.Camera.Prediction.Y, shared.Global.Camera.Prediction.Z))
													end
												else
													local L_512_ = L_290_.Character.HumanoidRootPart.Velocity
													L_509_ = L_503_.Position + (L_512_ * Vector3.new(
                                                                shared.Global.Camera.Prediction.X, shared.Global.Camera.Prediction.Y, shared.Global.Camera.Prediction.Z))
												end
												if shared.Global.Camera.Configurations.Value ~= 0 then
													if L_509_ then
														local L_513_ = L_316_.CFrame.Position
														local L_514_ = math.random(95, 105) / 100
														local L_515_ = shared.Global.Camera.Configurations.Value * L_514_
														local L_516_ = CFrame.new(L_513_, L_509_)
														L_316_.CFrame = L_316_.CFrame:Lerp(L_516_, L_515_)
													end
												end
											end
										end
									end
								end
							end
						end
					else
						if shared.Global.Camera.Configurations.ThirdPerson == false then
							if L_324_func() then
								if L_334_func(L_290_) then
									local L_517_ = L_290_.Character:FindFirstChild("Head")
									local L_518_ = L_290_.Character:FindFirstChild("LowerTorso")
									local L_519_ = nil
									if L_517_ and L_518_ then
										local L_520_ = L_335_func(L_517_)
										local L_521_ = L_335_func(L_518_)
										if L_520_ < L_521_ then
											L_519_ = L_517_
										else
											L_519_ = L_518_
										end
									elseif L_517_ then
										L_519_ = L_517_
									elseif L_518_ then
										L_519_ = L_518_
									end
									if L_519_ and L_331_func(L_290_, L_519_) then
										local L_522_ = L_519_.Position
										local L_523_ = L_290_.Character.HumanoidRootPart.Position
										local L_524_ = (L_522_ - L_523_).Magnitude
										if L_524_ <= math.sqrt(L_317_.X ^ 2 + L_317_.Y ^ 2 + L_317_.Z ^ 2) then
											local L_525_
											if shared.Global.Camera.Resolver then
												local L_526_ = L_290_.Character:FindFirstChildOfClass("Humanoid")
												if L_526_ then
													local L_527_ = L_526_.MoveDirection
													L_525_ = L_519_.Position + (L_527_ * Vector3.new(
                                                                shared.Global.Camera.Prediction.X, shared.Global.Camera.Prediction.Y, shared.Global.Camera.Prediction.Z))
												end
											else
												local L_528_ = L_290_.Character.HumanoidRootPart.Velocity
												L_525_ = L_519_.Position + (L_528_ * Vector3.new(
                                                            shared.Global.Camera.Prediction.X, shared.Global.Camera.Prediction.Y, shared.Global.Camera.Prediction.Z))
											end
											if shared.Global.Camera.Configurations.Value ~= 0 then
												if L_525_ then
													local L_529_ = L_316_.CFrame.Position
													local L_530_ = math.random(95, 105) / 100
													local L_531_ = (shared.Global.Camera.Configurations.Value * L_530_) * 0.1
													local L_532_ = CFrame.new(L_529_, L_525_)
													L_316_.CFrame = L_316_.CFrame:Lerp(L_532_, L_531_)
												end
											end
										end
									end
								end
							end
						end
					end
				end
			end
		end)
		L_285_.InputEnded:Connect(function(L_533_arg0, L_534_arg1)
			if L_533_arg0.KeyCode == Enum.KeyCode[shared.Global.Keys.Target:upper()] and shared.Global.Camera.Method == "hold" then  -- Updated to Keybind
				G_2_ = false
			end
		end)
		local L_336_ = game
		local L_337_ = L_336_:GetService("RunService")
		local L_338_ = L_336_:GetService("Players")
		local L_339_ = L_336_:GetService("UserInputService")
		local L_340_ = L_338_.LocalPlayer
		local L_341_ = L_340_:GetMouse()
		local L_342_ = L_336_:GetService("Workspace").CurrentCamera
		local L_343_ = L_336_:GetService("ReplicatedStorage")
		local L_344_ = L_336_:GetService("StarterGui")
		local L_345_ = L_336_:GetService("Workspace")
		local L_346_ = nil
		local L_347_ = Vector2.new
		local L_348_ = false
		local L_349_ = 0
		local L_350_ = {}
		if not game:IsLoaded() then
			game.Loaded:Wait()
		end
		local L_351_ = {
			DaHood = {
				ID = 2,
				Details = {
					Name = "Da Hood",
					Argument = "UpdateMousePosI2",
					Remote = "MainEvent",
					BodyEffects = "K.O"
				}
			},
			DaHoodMacro = {
				ID = 16033173781,
				Details = {
					Name = "Da Hood Macro",
					Argument = "UpdateMousePosI2",
					Remote = "MainEvent",
					BodyEffects = "K.O"
				}
			},
			DaHoodVC = {
				ID = 7213786345,
				Details = {
					Name = "Da Hood VC",
					Argument = "UpdateMousePosI",
					Remote = "MainEvent",
					BodyEffects = "K.O"
				}
			},
			HoodCustoms = {
				ID = 9825515356,
				Details = {
					Name = "Hood Customs",
					Argument = "GetMousePos",
					Remote = "MainEvent"
				}
			},
			HoodModded = {
				ID = 5602055394,
				Details = {
					Name = "Hood Modded",
					Argument = "MousePos",
					Remote = "Bullets"
				}
			},
			DaDownhillPSXbox = {
				ID = 77369032494150,
				Details = {
					Name = "Da Downhill [PS/Xbox]",
					Argument = "MOUSE",
					Remote = "MAINEVENT"
				}
			},
			DaBank = {
				ID = 132023669786646,
				Details = {
					Name = "Da Bank",
					Argument = "MOUSE",
					Remote = "MAINEVENT"
				}
			},
			DaUphill = {
				ID = 84366677940861,
				Details = {
					Name = "Da Uphill",
					Argument = "MOUSE",
					Remote = "MAINEVENT"
				}
			},
			DaHoodBotAimTrainer = {
				ID = 14487637618,
				Details = {
					Name = "Da Hood Bot Aim Trainer",
					Argument = "MOUSE",
					Remote = "MAINEVENT"
				}
			},
			HoodAimTrainer1v1 = {
				ID = 11143225577,
				Details = {
					Name = "1v1 Hood Aim Trainer",
					Argument = "UpdateMousePos",
					Remote = "MainEvent"
				}
			},
			HoodAim = {
				ID = 14413712255,
				Details = {
					Name = "Hood Aim",
					Argument = "MOUSE",
					Remote = "MAINEVENT"
				}
			},
			MoonHood = {
				ID = 14472848239,
				Details = {
					Name = "Moon Hood",
					Argument = "MoonUpdateMousePos",
					Remote = "MainEvent"
				}
			},
			DaStrike = {
				ID = 15186202290,
				Details = {
					Name = "Da Strike",
					Argument = "MOUSE",
					Remote = "MAINEVENT"
				}
			},
			OGDaHood = {
				ID = 17319408836,
				Details = {
					Name = "OG Da Hood",
					Argument = "UpdateMousePos",
					Remote = "MainEvent",
					BodyEffects = "K.O"
				}
			},
			DahAimTrainner = {
				ID = 12804651854,
				Details = {
					Name = "DahAimTrainner",
					Argument = "UpdateMousePos",
					Remote = "MainEvent",
					BodyEffects = "K.O"
				}
			},
			MekoHood = {
				ID = 17780567699,
				Details = {
					Name = "Meko Hood",
					Argument = "UpdateMousePos",
					Remote = "MainEvent",
					BodyEffects = "K.O"
				}
			},
			DaCraft = {
				ID = 128258288926907,
				Details = {
					Name = "Da Craft",
					Argument = "UpdateMousePos",
					Remote = "MainEvent",
					BodyEffects = "K.O"
				}
			},
			NewHood = {
				ID = 17809101348,
				Details = {
					Name = "New Hood",
					Argument = "UpdateMousePos",
					Remote = "MainEvent",
					BodyEffects = "K.O"
				}
			},
			NewHood2 = {
				ID = 138593053726293,
				Details = {
					Name = "New Hood",
					Argument = "UpdateMousePos",
					Remote = "MainEvent",
					BodyEffects = "K.O"
				}
			},
			DeeHood = {
				ID = 139379854239480,
				Details = {
					Name = "Dee Hood",
					Argument = "UpdateMousePos",
					Remote = "MainEvent",
					BodyEffects = "K.O"
				}
			},
			DerHood = {
				ID = 119024210985192,
				Details = {
					Name = "Dee Hood",
					Argument = "UpdateMousePos",
					Remote = "MainEvent",
					BodyEffects = "K.O"
				}
			},
			ARhood = {
				ID = 98930372136494,
				Details = {
					Name = "AR hood",
					Argument = "UpdateMousePos",
					Remote = "MainEvent",
					BodyEffects = "K.O"
				}
			},
			OGDahood = {
				ID = 76565633209271,
				Details = {
					Name = "OG da hood",
					Argument = "UpdateMousePos",
					Remote = "MainEvent",
					BodyEffects = "K.O"
				}
			},
			DelHoodAim = {
				ID = 88582222971530,
				Details = {
					Name = "Del Hood Aim",
					Argument = "UpdateMousePos",
					Remote = "MainEvent",
					BodyEffects = "K.O"
				}
			},
			DaKitty = {
				ID = 113357850268933,
				Details = {
					Name = "Da kitty",
					Argument = "UpdateMousePos",
					Remote = "MainEvent",
					BodyEffects = "K.O"
				}
			}
		}
		local L_352_ = game.PlaceId
		local L_353_
		for L_535_forvar0, L_536_forvar1 in pairs(L_351_) do
			if L_536_forvar1.ID == L_352_ then
				L_353_ = L_536_forvar1.Details
				break
			end
		end
		if not L_353_ then
			L_338_.LocalPlayer:Kick("Unsupported game")
			return
		end
		local L_354_ = L_353_.Remote
		local L_355_ = L_353_.Argument
		local L_356_ = L_353_.BodyEffects or "K.O"
		local L_357_ = game:GetService("ReplicatedStorage")
		local L_358_ = L_357_:FindFirstChild(L_354_)
		if not L_358_ then
			L_338_.LocalPlayer:Kick("Are you sure this is the correct game?")
			return
		end
		local function L_359_func(L_537_arg0)
			return L_537_arg0 == L_355_
		end
		local L_360_ = L_355_
		if L_359_func(L_360_) then
			L_358_:FireServer(L_360_)
		else
			L_338_.LocalPlayer:Kick("Invalid argument")
		end
		local function L_361_func()
			for L_538_forvar0, L_539_forvar1 in ipairs(L_350_) do
				L_539_forvar1:Destroy()
			end
			L_350_ = {}
		end
		local L_362_ = 35 / 5.5
		local function L_363_func(L_540_arg0, L_541_arg1, L_542_arg2)
			local L_543_ = 3.5
			local L_544_ = 12
			local L_545_ = L_540_arg0 * L_541_arg1 * L_542_arg2
			local L_546_ = L_544_ * (L_545_ / (L_543_ * L_543_ * L_543_))
			return L_546_
		end
		local function L_364_func()
			local L_547_ = L_340_.Character
			if not L_547_ then
				return nil
			end
			local L_548_ = L_547_:FindFirstChildOfClass("Tool")
			if L_548_ then
				return L_548_.Name
			end
			return nil
		end
		local function L_365_func()
			local L_549_ = L_364_func()
			local L_550_ = 3.5
			if G_1_ then
				if L_549_ == "db" then
					L_550_ = shared.Global.FOV['DoubleBarrelSG'][1] or 3.5
				elseif L_549_ == "rev" then
					L_550_ = shared.Global.FOV['Revolver'][1] or 3.5
				elseif L_549_ == "Tactical SG" then
					L_550_ = shared.Global.FOV['TacticalShotgun'][1] or 3.5
				elseif L_549_ == "Rifle" then
					L_550_ = shared.Global.FOV['Rifle'][1] or 3.5
				elseif L_549_ == "AUG" then
					L_550_ = shared.Global.FOV['AUG'][1] or 3.5
				end
				L_550_ = L_550_ * L_362_
				for L_551_forvar0, L_552_forvar1 in pairs(L_338_:GetPlayers()) do
					if L_552_forvar1.Character and L_552_forvar1 ~= L_340_ then
						local L_553_, L_554_ = pcall(function()
							return Death(L_552_forvar1)
						end)
						if L_553_ and not L_554_ then
							local L_555_, L_556_ = pcall(function()
								return GetClosestHitPoint(L_552_forvar1.Character)
							end)
							if L_555_ and L_556_ then
								local L_557_ = L_342_:WorldToScreenPoint(L_556_)
								local L_558_ = (L_347_(L_557_.X, L_557_.Y) - L_347_(L_341_.X, L_341_.Y)).Magnitude
								if L_558_ <= L_550_ then
								end
							end
						end
					end
				end
			end
		end
		L_337_.RenderStepped:Connect(L_365_func)
		local function L_366_func(L_559_arg0)
			if not shared.Global.Camera.Enabled or not G_1_ or not L_290_ then
				return false
			end
			local L_560_ = L_364_func()
			local L_561_ = 3.5
			if L_560_ == "db" then
				L_561_ = shared.Global.FOV['DoubleBarrelSG'][1] or 3.5
			elseif L_560_ == "rev" then
				L_561_ = shared.Global.FOV['Revolver'][1] or 3.5
			elseif L_560_ == "Tactical SG" then
				L_561_ = shared.Global.FOV['TacticalShotgun'][1] or 3.5
			elseif L_560_ == "Rifle" then
				L_561_ = shared.Global.FOV['Rifle'][1] or 3.5
			elseif L_560_ == "AUG" then
				L_561_ = shared.Global.FOV['AUG'][1] or 3.5
			end
			L_561_ = L_561_ * L_362_
			local L_562_, L_563_ = L_342_:WorldToScreenPoint(L_559_arg0.Position)
			local L_564_ = (L_347_(L_562_.X, L_562_.Y) - L_347_(L_341_.X, L_341_.Y)).Magnitude
			return L_563_ and L_564_ <= L_561_
		end
		local function L_367_func(L_565_arg0)
			if not shared.Global['Silent'].WallCheck then
				return true
			end
			local L_566_ = L_342_.CFrame.Position
			local L_567_ = (L_565_arg0.Position - L_566_).Unit * (L_565_arg0.Position - L_566_).Magnitude
			local L_568_ = Ray.new(L_566_, L_567_)
			local L_569_ = L_345_:FindPartOnRayWithIgnoreList(L_568_, {
				L_340_.Character,
				L_565_arg0.Parent
			})
			return L_569_ == L_565_arg0 or not L_569_
		end
		local function L_368_func(L_570_arg0)
			local L_571_ = nil
			local L_572_ = nil
			local L_573_ = math.huge
			local L_574_ = {
				"Head",
				"UpperTorso",
				"LowerTorso",
				"HumanoidRootPart",
				"LeftHand",
				"RightHand",
				"LeftLowerArm",
				"RightLowerArm",
				"LeftUpperArm",
				"RightUpperArm",
				"LeftFoot",
				"LeftLowerLeg",
				"LeftUpperLeg",
				"RightLowerLeg",
				"RightUpperLeg",
				"RightFoot"
			}
			for L_575_forvar0, L_576_forvar1 in pairs(L_574_) do
				local L_577_ = L_570_arg0:FindFirstChild(L_576_forvar1)
				if L_577_ and L_577_:IsA("BasePart") and L_366_func(L_577_) and L_367_func(L_577_) then
					local L_578_, L_579_ = L_342_:WorldToScreenPoint(L_577_.Position)
					local L_580_ = (L_347_(L_578_.X, L_578_.Y) - L_347_(L_341_.X, L_341_.Y)).Magnitude
					if L_580_ < L_573_ then
						L_571_ = L_577_
						L_572_ = L_577_.Position
						L_573_ = L_580_
					end
				end
			end
			return L_571_, L_572_
		end
		local L_369_ = shared.Global['Silent'].Prediction
		local function L_370_func(L_581_arg0, L_582_arg1)
			if L_581_arg0 and L_581_arg0.Character then
				local L_583_ = L_581_arg0.Character:FindFirstChildOfClass("Humanoid")
				if not L_583_ then
					return Vector3.new(0, 0, 0)
				end
				local L_584_ = L_581_arg0.Character[L_582_arg1].Velocity
				local L_585_ = shared.Global['Silent'].Prediction.X
				local L_586_ = shared.Global['Silent'].Prediction.Y
				local L_587_ = shared.Global['Silent'].Prediction.Z
				local L_588_ = Vector3.new(
                    L_584_.X * L_585_, L_584_.Y * L_586_, L_584_.Z * L_587_)
				if L_583_:GetState() == Enum.HumanoidStateType.Physics then
					L_588_ = L_588_ + Vector3.new(0, - 9.81 * L_586_, 0)
				end
				if L_588_.Y < -30 then
					shared.Global['Silent'].Prediction = {
						X = 0,
						Y = 0,
						Z = 0
					}
					return L_588_
				elseif L_588_.Magnitude > 50 then
					return L_583_.MoveDirection * 16 * L_585_
				else
					shared.Global['Silent'].Prediction = L_369_
					return L_588_
				end
			end
			return Vector3.new(0, 0, 0)
		end
		local function L_371_func()
			for L_589_forvar0, L_590_forvar1 in ipairs(L_350_) do
				L_590_forvar1:Destroy()
			end
			L_350_ = {}
		end
		local L_372_ = nil
		local function L_373_func(L_591_arg0)
			local L_592_ = game.Players.LocalPlayer.Character
			if not L_592_ then
				return false
			end
			local L_593_ = L_592_.Head.Position
			local L_594_ = (L_591_arg0 - L_593_).Unit * 1000
			local L_595_ = RaycastParams.new()
			L_595_.FilterType = Enum.RaycastFilterType.Blacklist
			L_595_.FilterDescendantsInstances = {
				L_592_
			}
			local L_596_, L_597_ = pcall(function()
				return workspace:Raycast(L_593_, L_594_, L_595_)
			end)
			return L_596_ and L_597_ and (L_597_.Position - L_591_arg0).Magnitude < 5
		end
		L_315_.RenderStepped:Connect(function()
			local L_598_ = game.Players.LocalPlayer.Character
			if L_598_ and L_598_:FindFirstChild("Humanoid") then
				local L_599_ = L_598_.Humanoid
				local L_600_, L_601_ = pcall(function()
					if L_599_.Health <= 1 then
						L_290_ = nil
						G_1_ = false
						L_372_ = nil
						return
					end
				end)
			end
			if shared.Global['Silent'].Enabled and G_1_ then
				if L_290_ then
					if L_290_.Character then
						local L_602_ = L_290_.Character.Head.Position
						local L_603_, L_604_ = pcall(function()
							if L_290_.Character.Humanoid.Health < 1 then
								L_290_ = nil
								G_1_ = false
								L_372_ = nil
								return
							end
						end)
						local L_605_, L_606_ = pcall(function()
							if Death(L_290_) then
								L_290_ = nil
								G_1_ = false
								L_372_ = nil
								return
							end
						end)
						local L_607_, L_608_, L_609_ = pcall(function()
							if not L_373_func(L_602_) then
								G_1_ = false
								L_372_ = L_290_
								return
							end
							return L_368_func(L_290_.Character)
						end)
						if L_607_ and L_608_ and L_609_ then
							local L_610_ = L_370_func(L_290_, L_608_.Name)
							local L_611_ = shared.Global['Silent'].Prediction.X
							local L_612_ = shared.Global['Silent'].Prediction.Y
							local L_613_ = shared.Global['Silent'].Prediction.Z
							local L_614_ = L_610_ * Vector3.new(L_611_, L_612_, L_613_)
							local L_615_, L_616_ = pcall(function()
								L_343_[L_354_]:FireServer(L_355_, L_609_ + L_614_)
							end)
						end
					end
				end
			elseif L_372_ and L_372_.Character then
				local L_617_ = L_372_.Character.Head.Position
				local L_618_, L_619_ = pcall(function()
					if L_373_func(L_617_) then
						L_290_ = L_372_
						G_1_ = true
						L_372_ = nil
					end
				end)
			else
			end
		end)
		local function L_374_func(L_620_arg0)
			if L_620_arg0:IsA("Tool") then
				L_620_arg0.Activated:Connect(function()
					if tick() - L_349_ > 0.1 then
						L_349_ = tick()
						local L_621_, L_622_ = pcall(function()
							return L_290_
						end)
						if L_621_ and L_622_ and L_622_.Character then
							local L_623_, L_624_, L_625_ = pcall(function()
								return L_368_func(L_622_.Character)
							end)
							if L_623_ and L_624_ and L_625_ then
								local L_626_, L_627_ = pcall(function()
									return L_370_func(L_622_, L_624_.Name)
								end)
								if L_626_ and L_627_ then
									local L_628_ = shared.Global['Silent'].Prediction.X
									local L_629_ = shared.Global['Silent'].Prediction.Y
									local L_630_ = shared.Global['Silent'].Prediction.Z
									local L_631_ = L_627_ * Vector3.new(L_628_, L_629_, L_630_)
									local L_632_, L_633_ = pcall(function()
										L_343_[L_354_]:FireServer(L_355_, L_625_ + L_631_)
									end)
								end
							end
						end
					end
				end)
			end
		end
		local function L_375_func(L_634_arg0)
			L_634_arg0.ChildAdded:Connect(function(L_635_arg0)
				local L_636_, L_637_ = pcall(function()
					L_374_func(L_635_arg0)
				end)
			end)
			for L_638_forvar0, L_639_forvar1 in pairs(L_634_arg0:GetChildren()) do
				local L_640_, L_641_ = pcall(function()
					L_374_func(L_639_forvar1)
				end)
			end
		end
		local L_376_, L_377_ = pcall(function()
			L_340_.CharacterAdded:Connect(L_375_func)
		end)
		if L_340_.Character then
			pcall(function()
				L_375_func(L_340_.Character)
			end)
		end
	end
	if table.find(L_269_, L_270_) then
		local L_642_ = L_272_func(L_268_)
		if L_642_ then
			L_642_:GetPropertyChangedSignal("Visible"):Connect(function()
				if L_642_.Visible and not L_271_ then
					L_271_ = true
					L_273_func()
				end
			end)
		else
			L_273_func()
		end
	else
		L_273_func()
	end
end
game:GetService('RunService').RenderStepped:Connect(function()
	local L_643_ = shared.Global.Hitbox
	if not L_643_.Enabled then
		return
	end
	local L_644_ = game:GetService('Players').LocalPlayer
	if not L_644_ or not L_644_.Character then
		return
	end
	local L_645_ = L_644_.Character
	local L_646_ = L_645_:FindFirstChildOfClass("Tool")
	if not L_646_ then
		return
	end
	local L_647_ = L_643_.Guns[L_646_.Name]
	if not L_647_ then
		return
	end
	local L_648_ = L_644_.Team
	for L_649_forvar0, L_650_forvar1 in ipairs(game:GetService('Players'):GetPlayers()) do
		if L_650_forvar1 ~= L_644_ and (not IsTeamCheckEnabled or L_650_forvar1.Team ~= L_648_) then
			local L_651_ = L_650_forvar1.Character
			local L_652_ = L_651_ and L_651_:FindFirstChild("HumanoidRootPart")
			if L_652_ then
				local L_653_ = L_647_.H or 0.4
				local L_654_ = L_647_.W or 0.4
				if TargetPlayer == L_650_forvar1 then
					L_652_.Size = Vector3.new(L_654_ * 2, L_653_ * 2, L_654_ * 2)
					L_652_.CanCollide = false
				else
					L_652_.Size = Vector3.new(2, 2, 2)
					L_652_.CanCollide = false
				end
			end
		end
	end
end)
local L_1_ = {}
L_1_ = {
	cache = {},
	init = function(L_655_arg0)
		if not L_1_.cache[L_655_arg0] then
			L_1_.cache[L_655_arg0] = {
				data = L_1_[L_655_arg0](),
			}
		end
		return L_1_.cache[L_655_arg0].data
	end,
}
do
	function L_1_.process1()
		return 10
	end
	function L_1_.process2()
		local L_656_ = {}
		local L_657_ = game:GetService('Players')
		local L_658_ = workspace.CurrentCamera
		function L_656_.calculate(L_659_arg0, L_660_arg1)
			if typeof(L_659_arg0) == 'string' then
				return L_659_arg0
			end
			local L_661_ = 10 ^ (L_660_arg1 or 0)
			local L_662_ = math.floor(L_659_arg0 * L_661_ + 0.5) / L_661_
			local L_663_, L_664_ = math.modf(L_662_)
			if L_664_ == 0 then
				return string.format('%.0f', L_662_) .. '.00'
			else
				return string.format('%.' .. L_660_arg1 .. 'f', L_662_)
			end
		end
		function L_656_.toVector2(L_665_arg0)
			return Vector2.new(L_665_arg0.X, L_665_arg0.Y)
		end
	end
	L_1_.process1()
	L_1_.process2()
end
local L_2_ = {}
L_2_ = {
	finalize = function()
		local L_666_, L_667_, L_668_ = 0, 0, 0
		local L_669_ = {}
		local function L_670_func(L_675_arg0, L_676_arg1, L_677_arg2)
			return L_675_arg0 * L_676_arg1 + L_677_arg2
		end
		local function L_671_func(L_678_arg0, L_679_arg1, L_680_arg2)
			return L_678_arg0 - L_679_arg1 / L_680_arg2
		end
		local function L_672_func(L_681_arg0)
			local L_682_ = {}
			for L_683_forvar0 = 1, # L_681_arg0 do
				L_682_[L_683_forvar0] = L_670_func(L_681_arg0[L_683_forvar0], L_683_forvar0, L_668_) + L_671_func(L_681_arg0[L_683_forvar0], L_667_, 2)
			end
			return L_682_
		end
		local L_673_ = {
			3,
			5,
			7,
			9,
			11
		}
		L_669_ = L_672_func(L_673_)
		local L_674_ = # L_673_
		for L_684_forvar0 = 1, L_674_ do
			local L_685_ = math.sqrt(L_670_func(L_673_[L_684_forvar0], L_666_, L_667_) + L_671_func(L_673_[L_684_forvar0], L_668_, 1))
			table.insert(L_669_, L_685_)
		end
	end,
	secure = function()
		local L_686_, L_687_ = 0, 0
		local L_688_ = {}
		local function L_689_func(L_691_arg0, L_692_arg1, L_693_arg2)
			return L_691_arg0 + L_692_arg1 - L_693_arg2
		end
		local function L_690_func(L_694_arg0, L_695_arg1, L_696_arg2)
			return L_694_arg0 * L_695_arg1 / L_696_arg2
		end
		for L_697_forvar0 = 1, 10 do
			L_688_[L_697_forvar0] = L_689_func(L_697_forvar0, L_686_, L_687_)
		end
		for L_698_forvar0 = 1, # L_688_ do
			L_686_ = L_690_func(L_688_[L_698_forvar0], L_686_, L_687_)
		end
	end
}
L_2_.finalize()
L_2_.secure()
local L_3_ = {}
L_3_ = {
	safeguard = function()
		local L_699_, L_700_ = 1, 0
		local function L_701_func(L_702_arg0, L_703_arg1)
			return L_702_arg0 * L_703_arg1
		end
		for L_704_forvar0 = 1, 100 do
			L_699_ = L_701_func(L_699_, L_704_forvar0)
			L_700_ = L_700_ + L_704_forvar0
		end
	end
}
L_3_.safeguard()
