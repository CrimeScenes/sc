  if game.PlaceId == 113959651351894 or game.PlaceId == 2788229376 then
                print("bypassed lol:?")
                 

                if shared.Global.Memory.Settings.Enabled == true then
                 local Memory = tostring(math.random(shared.Global.Memory.Configuration.Start, shared.Global.Memory.Configuration.End)) .. "." .. tostring(math.random(10, 99)) 
                 
                 
                 game:GetService("RunService").RenderStepped:Connect(function()
                    pcall(function()
                        for i, v in pairs(game:GetService("CoreGui").RobloxGui.PerformanceStats:GetChildren()) do
                            if v.Name == "PS_Button" then
                                if v.StatsMiniTextPanelClass.TitleLabel.Text == "Mem" then
                                    v.StatsMiniTextPanelClass.ValueLabel.Text = tostring(Memory) .. " MB"
                                end
                            end
                        end
                    end)
                 
                    pcall(function()
                        if game:GetService("CoreGui").RobloxGui.PerformanceStats["PS_Viewer"].Frame.TextLabel.Text == "Memory" then
                            for i, v in pairs(game:GetService("CoreGui").RobloxGui.PerformanceStats["PS_Viewer"].Frame:GetChildren()) do
                                if v.Name == "PS_DecoratedValueLabel" and string.find(v.Label.Text, 'Current') then
                                    v.Label.Text = "Current: " .. Memory .. " MB"
                                end
                                if v.Name == "PS_DecoratedValueLabel" and string.find(v.Label.Text, 'Average') then
                                    v.Label.Text = "Average: " .. Memory .. " MB"
                                end
                            end
                        end
                    end)
                 
                    pcall(function()
                        game:GetService("CoreGui").DevConsoleMaster.DevConsoleWindow.DevConsoleUI.TopBar.LiveStatsModule["MemoryUsage_MB"].Text = math.round(tonumber(Memory)) .. " MB"
                    end)
                 end)
                 
                 
                 task.spawn(function()
                    while task.wait(1) do
                        local minMemory = shared.Global.Memory.Configuration.Start
                        local maxMemory = shared.Global.Memory.Configuration.End
                        Memory = tostring(math.random(minMemory, maxMemory)) .. "." .. tostring(math.random(10, 99))
                    end
                 end)
                 end
                 
                 
                 
                 local UserInputService = game:GetService('UserInputService')
           
                 local function sortInventory()
                     if not shared.Global.Inventory.Enabled then  -- Make sure 'Inventory' is accessed correctly
                         return
                     end
             
                     local player = game.Players.LocalPlayer
                     local backpack = player:FindFirstChildOfClass("Backpack")
                     if not backpack then
                         return
                     end
             
                     local originalItems = {}
                     for _, tool in pairs(backpack:GetChildren()) do
                         table.insert(originalItems, tool)
                     end
             
                     -- Clear the backpack
                     for _, tool in pairs(backpack:GetChildren()) do
                         tool.Parent = nil
                     end
             
                     -- Function to get the item's order
                     local function getItemOrder(itemName)
                         return shared.Global.Inventory.Order[itemName.Name] or 999
                     end
             
                     -- Sort the items based on order
                     table.sort(originalItems, function(a, b)
                         local orderA = getItemOrder(a)
                         local orderB = getItemOrder(b)
                         return orderA < orderB
                     end)
             
                     -- Put the items back in the backpack in the sorted order
                     for i, tool in ipairs(originalItems) do
                         local order = getItemOrder(tool)
                         local slot = math.min(order, 10)  -- Optional: limit to 10 slots, adjust if needed
                         tool.Parent = backpack
                     end
                 end
             
                 -- Handle key input and trigger inventory sort
                 UserInputService.InputBegan:Connect(function(input, isProcessed)
                     if isProcessed then
                         return
                     end
                     -- Compare input key with the keybind ('E' as string)
                     if input.KeyCode.Name == shared.Global.Inventory.Keybind then
                         sortInventory()
                     end
                 end)
                 
                 
                 
                 
                 
                 
                 
                 local Players = game:GetService("Players")
                 local LocalPlayer = Players.LocalPlayer
                 local Camera = game:GetService("Workspace").CurrentCamera
                 local UserInputService = game:GetService("UserInputService")
                 local RunService = game:GetService("RunService")
                 local VirtualInputManager = game:GetService("VirtualInputManager")
                 local lastClickTime = 0
                 local isToggled = false
                 local TargetPlayer = nil
                 
                 local config = shared.Global.Trigger
                 local Delay = config.Delay
                 local Key = config.Activation.Keybind
                 local TriggerEnabled = config.Enabled
                 local FOV = config.FOV
                 local Prediction = config.Prediction
                 local Mode = config.Activation.Mode
                 
                 if Delay >= 0.06 then
                     Delay = 0.001
                 elseif Delay < 0.06 then
                     if Delay < 0.0001 then
                         Delay = 0.0001
                     end
                 end
                 
                 local closeRangeHitboxSize = 0.9
                 local midRangeHitboxSize = 0.6
                 local farRangeHitboxSize = 0.3
                 
                 local AllBodyParts = {
                     "Head", "UpperTorso", "LowerTorso", "HumanoidRootPart", "LeftHand", "RightHand", 
                     "LeftLowerArm", "RightLowerArm", "LeftUpperArm", "RightUpperArm", "LeftFoot", 
                     "LeftLowerLeg", "LeftUpperLeg", "RightLowerLeg", "RightUpperLeg", "RightFoot"
                 }
                 
                 local function mouse1click(x, y)
                     VirtualInputManager:SendMouseButtonEvent(x, y, 0, true, game, false)
                     VirtualInputManager:SendMouseButtonEvent(x, y, 0, false, game, false)
                 end
                 
                 local function getMousePosition()
                     local mouse = UserInputService:GetMouseLocation()
                     return mouse.X, mouse.Y
                 end
                 
                 local function isWithinBox(position)
                     local screenPos = Camera:WorldToViewportPoint(position)
                     local screenCenter = Vector2.new(Camera.ViewportSize.X / 2, Camera.ViewportSize.Y / 2)
                     return (Vector2.new(screenPos.X, screenPos.Y) - screenCenter).Magnitude <= Camera.ViewportSize.X / 2
                 end
                 
                 local function isMouseOnTarget(targetPlayer)
                     local mouse = LocalPlayer:GetMouse()
                     return mouse.Target and mouse.Target:IsDescendantOf(targetPlayer.Character)
                 end
                 
                 local function isIgnoringKnife()
                     local currentTool = LocalPlayer.Character:FindFirstChildOfClass("Tool")
                     if currentTool then
                         local toolName = currentTool.Name:lower()
                         return toolName == "knife" or toolName == "katana" or toolName == "[knife]" or toolName == "[katana]"
                     end
                     return false
                 end
                 
                 local function getVelocity(player)
                     local humanoidRootPart = player.Character:FindFirstChild("HumanoidRootPart")
                     if humanoidRootPart then
                         local velocity = humanoidRootPart.AssemblyLinearVelocity
                         return velocity
                     end
                     return Vector3.zero
                 end
                 
                 -- Prediction method similar to the first code you provided
                 local function predictTargetPosition(targetPlayer, deltaTime)
                     local partToPredict = targetPlayer.Character:FindFirstChild("HumanoidRootPart")
                     if not partToPredict then return targetPlayer.Character.HumanoidRootPart.Position end
                 
                     -- Get velocity and apply distortion (Prediction.X, Prediction.Y, Prediction.Z)
                     local velocity = getVelocity(targetPlayer)
                     local adjustedVelocity = Vector3.new(
                         velocity.X * Prediction.X,
                         velocity.Y * Prediction.Y,
                         velocity.Z * Prediction.Z
                     )
                 
                     -- Predict future position based on velocity
                     local predictedPos = partToPredict.Position + adjustedVelocity * deltaTime
                     return predictedPos
                 end
                 
                 local function calculateHitboxSize(distance)
                     if distance <= 18 then  
                         return closeRangeHitboxSize
                     elseif distance > 18 and distance <= 30 then 
                         return midRangeHitboxSize
                     else  
                         return farRangeHitboxSize
                     end
                 end
                 
                 local function updateFOVBasedOnRange(distance)
                     if distance <= 18 then
                         return FOV.Close
                     elseif distance > 18 and distance <= 30 then
                         return FOV.Middle
                     else
                         return FOV.Far
                     end
                 end
                 
                 local function aimAtTargetBody(targetPlayer)
                     for _, bodyPartName in pairs(AllBodyParts) do
                         local bodyPart = targetPlayer.Character:FindFirstChild(bodyPartName)
                         if bodyPart and bodyPart:IsDescendantOf(targetPlayer.Character) then
                             local targetPos = bodyPart.Position
                             local distance = (targetPos - Camera.CFrame.Position).Magnitude
                 
                             local currentFOV = updateFOVBasedOnRange(distance)
                             local hitboxSize = calculateHitboxSize(distance)
                 
                             -- Use the prediction function to calculate where the target will be
                             local predictedPos = predictTargetPosition(targetPlayer, 0.1)
                             local screenPos, onScreen = Camera:WorldToViewportPoint(predictedPos)
                             if onScreen and isWithinBox(predictedPos) then
                                 local mousePos = Vector2.new(screenPos.X, screenPos.Y)
                                 local adjustedMousePos = mousePos + Vector2.new(math.random(-hitboxSize * 7, hitboxSize * 7), math.random(-hitboxSize * 7, hitboxSize * 7))
                                 VirtualInputManager:SendMouseMoveEvent(adjustedMousePos.X, adjustedMousePos.Y, game)
                 
                                 if os.clock() - lastClickTime >= Delay and not isIgnoringKnife() then
                                     lastClickTime = os.clock()
                 
                                     local mouseX, mouseY = getMousePosition()
                                     mouse1click(mouseX, mouseY)
                                 end
                             end
                         end
                     end
                 end
                 
                 local function TriggerAction()
                     if TargetPlayer and TargetPlayer.Character then
                         local humanoid = TargetPlayer.Character:FindFirstChild("Humanoid")
                         if humanoid and humanoid.Health > 0 then
                             if isMouseOnTarget(TargetPlayer) then
                                 aimAtTargetBody(TargetPlayer)
                             end
                         end
                     end
                 end
                 
                 UserInputService.InputBegan:Connect(function(input, gameProcessed)
                     if not gameProcessed and input.KeyCode.Name == Key then
                         if Mode == "toggle" then
                             isToggled = not isToggled  
                         elseif Mode == "hold" then
                             isToggled = true
                         end
                     end
                 end)
                 
                 UserInputService.InputEnded:Connect(function(input, gameProcessed)
                     if not gameProcessed and input.KeyCode.Name == Key and Mode == "hold" then
                         isToggled = false  
                     end
                 end)
                 
                 RunService.RenderStepped:Connect(function()
                     if TriggerEnabled and isToggled then  
                         TriggerAction()  
                     end
                 end)
                 
                 
                 
                 
                 
                 
                 local Players = game:GetService("Players")
local LocalPlayer = Players.LocalPlayer
local Mouse = LocalPlayer:GetMouse()
local RunService = game:GetService("RunService")
local Camera = game.Workspace.CurrentCamera

local FOV = shared.Global.Camera.FOV

local isRightMouseButtonDown = false

local whitelist = shared.Global.Core.Checks.Whitelist

-- Threshold for determining first-person (the camera is close to the character)
local FIRST_PERSON_THRESHOLD = 5 -- Camera within 5 studs of the player is considered first-person
local THIRD_PERSON_THRESHOLD = 10 -- Camera beyond 10 studs is considered third-person

-- Function to determine if the player is in first-person mode
local function IsFirstPerson()
 local playerPosition = LocalPlayer.Character and LocalPlayer.Character:FindFirstChild("HumanoidRootPart")
 if playerPosition then
     local cameraPosition = Camera.CFrame.Position
     local distance = (cameraPosition - playerPosition.Position).Magnitude
     return distance < FIRST_PERSON_THRESHOLD
 end
 return false
end

-- Function to determine if the player is in third-person mode
local function IsThirdPerson()
 local playerPosition = LocalPlayer.Character and LocalPlayer.Character:FindFirstChild("HumanoidRootPart")
 if playerPosition then
     local cameraPosition = Camera.CFrame.Position
     local distance = (cameraPosition - playerPosition.Position).Magnitude
     return distance > THIRD_PERSON_THRESHOLD
 end
 return false
end

local function UpdateFOV()
 -- You can update the FOV here if needed, but it's not necessary for the logic
end

RunService.RenderStepped:Connect(UpdateFOV)

local function ClosestPlrFromMouse()
 local Target, Closest = nil, math.huge
 local enabled = shared.Global.Core.Enabled
 local whitelist = shared.Global.Core.Checks.Whitelist

 for _, player in pairs(Players:GetPlayers()) do
     if player ~= LocalPlayer and player.Character and player.Character:FindFirstChild("HumanoidRootPart") then
         if enabled and table.find(whitelist, player.Name) then
             break
         end

         local Position, OnScreen = Camera:WorldToScreenPoint(player.Character.HumanoidRootPart.Position)
         local Distance = (Vector2.new(Position.X, Position.Y) - Vector2.new(Mouse.X, Mouse.Y)).Magnitude

         if Distance < Closest and OnScreen then
             Closest = Distance
             Target = player
         end
     end
 end
 return Target
end

local function GetClosestBodyPart(character)
 local ClosestDistance = math.huge
 local BodyPart = nil

 if character and character:IsDescendantOf(game.Workspace) then
     for _, part in ipairs(character:GetChildren()) do
         if part:IsA("BasePart") then
             local Position, OnScreen = Camera:WorldToScreenPoint(part.Position)
             if OnScreen then
                 local Distance = (Vector2.new(Position.X, Position.Y) - Vector2.new(Mouse.X, Mouse.Y)).Magnitude
                 if Distance < ClosestDistance then
                     ClosestDistance = Distance
                     BodyPart = part
                 end
             end
         end
     end
 end
 return BodyPart
end

local function GetTarget()
 return TargetPlayer
end

Mouse.Button2Down:Connect(function()
 if shared.Global.Camera.MouseButton2 then
     isRightMouseButtonDown = true
 end
end)

Mouse.Button2Up:Connect(function()
 isRightMouseButtonDown = false
end)

-- Wall check to see if the target is visible
local function IsTargetVisible(targetPlayer, bodyPart)
 if targetPlayer and bodyPart then
     -- Cast a ray from the camera to the target's body part
     local ray = Ray.new(Camera.CFrame.Position, (bodyPart.Position - Camera.CFrame.Position).unit * 500)
     local hitPart = workspace:FindPartOnRay(ray, LocalPlayer.Character)
     
     -- If the ray hits something that isn't the target's body part, there's a wall
     if hitPart and hitPart.Parent ~= targetPlayer.Character then
         return false
     end
 end
 return true
end

-- Main targeting logic
Mouse.KeyDown:Connect(function(Key)
 local key = Key:lower()

 if key == shared.Global.Core.Keybind:lower() then
     if shared.Global.Camera.Enabled then
         if IsTargeting then
             if TargetPlayer and TargetPlayer.Character and TargetPlayer.Character:FindFirstChildOfClass("Humanoid") then
                 if TargetPlayer.Character.Humanoid.Health >= 1 then
                     if ClosestPlrFromMouse() == TargetPlayer then
                         -- Already targeting the closest player
                     else
                         local newTarget = ClosestPlrFromMouse()
                         if newTarget and newTarget.Character and newTarget.Character:FindFirstChildOfClass("Humanoid").Health >= 1 then
                             TargetPlayer = newTarget
                         end
                     end
                 else
                     TargetPlayer = nil
                     IsTargeting = false
                 end
             end
         else
             local initialTarget = ClosestPlrFromMouse()
             if initialTarget and initialTarget.Character and initialTarget.Character:FindFirstChildOfClass("Humanoid").Health >= 1 then
                 IsTargeting = true
                 TargetPlayer = initialTarget
             end
         end
     end
 end

 if key == shared.Global.Core.Cancel:lower() then  
     IsTargeting = false
     TargetPlayer = nil
 end
end)

local function IsAlignedWithCamera(targetPlayer)
 if targetPlayer and targetPlayer.Character then
     local targetPosition = targetPlayer.Character.HumanoidRootPart.Position
     local cameraPosition = Camera.CFrame.Position
     local direction = (targetPosition - cameraPosition).unit
     local targetDirection = Camera.CFrame.LookVector.unit
     return direction:Dot(targetDirection) > 0.9
 end
 return false
end

local function GetDistanceFromMouse(bodyPart)
 local mousePosition = game:GetService("Players").LocalPlayer:GetMouse().Hit.p
 return (bodyPart.Position - mousePosition).Magnitude
end

RunService.RenderStepped:Connect(function()
 if IsTargeting and TargetPlayer and TargetPlayer.Character then
     if TargetPlayer.Character:FindFirstChildOfClass("Humanoid") and TargetPlayer.Character.Humanoid.Health < 2 then
         TargetPlayer = nil
         IsTargeting = false
         return
     end

     if shared.Global.Camera.Enabled then
         if shared.Global.Camera.MouseButton2 then
             if isRightMouseButtonDown then
                 if shared.Global.Camera.Configurations.ThirdPerson == false then
                     if IsFirstPerson() then
                         if IsAlignedWithCamera(TargetPlayer) then
                             local head = TargetPlayer.Character:FindFirstChild("Head")
                             local lowerTorso = TargetPlayer.Character:FindFirstChild("LowerTorso")

                             local bodyPart = nil
                             if head and lowerTorso then
                                 local distanceToHead = GetDistanceFromMouse(head)
                                 local distanceToLowerTorso = GetDistanceFromMouse(lowerTorso)

                                 if distanceToHead < distanceToLowerTorso then
                                     bodyPart = head
                                 else
                                     bodyPart = lowerTorso
                                 end
                             elseif head then
                                 bodyPart = head
                             elseif lowerTorso then
                                 bodyPart = lowerTorso
                             end

                             if bodyPart and IsTargetVisible(TargetPlayer, bodyPart) then
                                 local targetPosition = bodyPart.Position
                                 local playerPosition = TargetPlayer.Character.HumanoidRootPart.Position
                                 local distanceToTarget = (targetPosition - playerPosition).Magnitude

                                 if distanceToTarget <= math.sqrt(FOV.X^2 + FOV.Y^2 + FOV.Z^2) then
                                     local predictedPosition
                                     if shared.Global.Camera.Resolver then
                                         local humanoid = TargetPlayer.Character:FindFirstChildOfClass("Humanoid")
                                         if humanoid then
                                             local moveDirection = humanoid.MoveDirection
                                             predictedPosition = bodyPart.Position + (moveDirection * Vector3.new(
                                                 shared.Global.Camera.Prediction.X,
                                                 shared.Global.Camera.Prediction.Y,
                                                 shared.Global.Camera.Prediction.Z
                                             ))
                                         end
                                     else
                                         local targetVelocity = TargetPlayer.Character.HumanoidRootPart.Velocity
                                         predictedPosition = bodyPart.Position + (targetVelocity * Vector3.new(
                                             shared.Global.Camera.Prediction.X,
                                             shared.Global.Camera.Prediction.Y,
                                             shared.Global.Camera.Prediction.Z
                                         ))
                                     end

                                     if predictedPosition then
                                         local DesiredCFrame = CFrame.new(Camera.CFrame.Position, predictedPosition)
                                         Camera.CFrame = Camera.CFrame:Lerp(DesiredCFrame, shared.Global.Camera.Configurations.Value)
                                     end
                                 end
                             end
                         end
                     end
                 end
             end
         else
             if shared.Global.Camera.Configurations.ThirdPerson == false then
                 if IsFirstPerson() then
                     if IsAlignedWithCamera(TargetPlayer) then
                         local head = TargetPlayer.Character:FindFirstChild("Head")
                         local lowerTorso = TargetPlayer.Character:FindFirstChild("LowerTorso")

                         local bodyPart = nil
                         if head and lowerTorso then
                             local distanceToHead = GetDistanceFromMouse(head)
                             local distanceToLowerTorso = GetDistanceFromMouse(lowerTorso)

                             if distanceToHead < distanceToLowerTorso then
                                 bodyPart = head
                             else
                                 bodyPart = lowerTorso
                             end
                         elseif head then
                             bodyPart = head
                         elseif lowerTorso then
                             bodyPart = lowerTorso
                         end

                         if bodyPart and IsTargetVisible(TargetPlayer, bodyPart) then
                             local targetPosition = bodyPart.Position
                             local playerPosition = TargetPlayer.Character.HumanoidRootPart.Position
                             local distanceToTarget = (targetPosition - playerPosition).Magnitude

                             if distanceToTarget <= math.sqrt(FOV.X^2 + FOV.Y^2 + FOV.Z^2) then
                                 local predictedPosition
                                 if shared.Global.Camera.Resolver then
                                     local humanoid = TargetPlayer.Character:FindFirstChildOfClass("Humanoid")
                                     if humanoid then
                                         local moveDirection = humanoid.MoveDirection
                                         predictedPosition = bodyPart.Position + (moveDirection * Vector3.new(
                                             shared.Global.Camera.Prediction.X,
                                             shared.Global.Camera.Prediction.Y,
                                             shared.Global.Camera.Prediction.Z
                                         ))
                                     end
                                 else
                                     local targetVelocity = TargetPlayer.Character.HumanoidRootPart.Velocity
                                     predictedPosition = bodyPart.Position + (targetVelocity * Vector3.new(
                                         shared.Global.Camera.Prediction.X,
                                         shared.Global.Camera.Prediction.Y,
                                         shared.Global.Camera.Prediction.Z
                                     ))
                                 end

                                 if predictedPosition then
                                     local DesiredCFrame = CFrame.new(Camera.CFrame.Position, predictedPosition)
                                     Camera.CFrame = Camera.CFrame:Lerp(DesiredCFrame, shared.Global.Camera.Configurations.Value)
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


                 UserInputService.InputEnded:Connect(function(input, isProcessed)
                     if input.KeyCode == Enum.KeyCode[shared.Global.Core.Keybind:upper()] and shared.Global.Camera.Method == "hold" then  -- Updated to Keybind
                         holdingMouseButton = false
                     end
                 end)


             else

             local player = game.Players.LocalPlayer
             local playerGui = player.PlayerGui
             
             local gameIdsToWaitFor = {15186202290, 9825515356, 84366677940861, 77369032494150, 132023669786646}  
             local currentGameId = game.PlaceId  
             
             local scriptExecuted = false
             
            
             local function findSettingsFrame(gui)
                 for _, child in ipairs(gui:GetChildren()) do
                     if child.Name == "Settings" then
                         return child
                     end
                     local result = findSettingsFrame(child)
                     if result then
                         return result
                     end
                 end
                 return nil
             end
             
            
             local function handleSettingsOpened()
             
             
             if shared.Global.Memory.Settings.Enabled == true then
             local Memory = tostring(math.random(shared.Global.Memory.Configuration.Start, shared.Global.Memory.Configuration.End)) .. "." .. tostring(math.random(10, 99)) -- Initialize with a valid memory value
             
             
             game:GetService("RunService").RenderStepped:Connect(function()
                pcall(function()
                    for i, v in pairs(game:GetService("CoreGui").RobloxGui.PerformanceStats:GetChildren()) do
                        if v.Name == "PS_Button" then
                            if v.StatsMiniTextPanelClass.TitleLabel.Text == "Mem" then
                                v.StatsMiniTextPanelClass.ValueLabel.Text = tostring(Memory) .. " MB"
                            end
                        end
                    end
                end)
             
                pcall(function()
                    if game:GetService("CoreGui").RobloxGui.PerformanceStats["PS_Viewer"].Frame.TextLabel.Text == "Memory" then
                        for i, v in pairs(game:GetService("CoreGui").RobloxGui.PerformanceStats["PS_Viewer"].Frame:GetChildren()) do
                            if v.Name == "PS_DecoratedValueLabel" and string.find(v.Label.Text, 'Current') then
                                v.Label.Text = "Current: " .. Memory .. " MB"
                            end
                            if v.Name == "PS_DecoratedValueLabel" and string.find(v.Label.Text, 'Average') then
                                v.Label.Text = "Average: " .. Memory .. " MB"
                            end
                        end
                    end
                end)
             
                pcall(function()
                    game:GetService("CoreGui").DevConsoleMaster.DevConsoleWindow.DevConsoleUI.TopBar.LiveStatsModule["MemoryUsage_MB"].Text = math.round(tonumber(Memory)) .. " MB"
                end)
             end)
             
             
             task.spawn(function()
                while task.wait(1) do
                    local minMemory = shared.Global.Memory.Configuration.Start
                    local maxMemory = shared.Global.Memory.Configuration.End
                    Memory = tostring(math.random(minMemory, maxMemory)) .. "." .. tostring(math.random(10, 99))
                end
             end)
             end
             
             
             
             
             
             
             
             
             
             
             
             local Players = game:GetService("Players")
             local LocalPlayer = Players.LocalPlayer
             local Camera = game:GetService("Workspace").CurrentCamera
             local UserInputService = game:GetService("UserInputService")
             local RunService = game:GetService("RunService")
             local VirtualInputManager = game:GetService("VirtualInputManager")
             local lastClickTime = 0
             local isToggled = false
             local TargetPlayer = nil
             
             local config = shared.Global.Trigger
             local Delay = config.Delay
             local Key = config.Activation.Keybind
             local TriggerEnabled = config.Enabled
             local FOV = config.FOV
             local Prediction = config.Prediction
             local Mode = config.Activation.Mode
             
             if Delay >= 0.06 then
                 Delay = 0.001
             elseif Delay < 0.06 then
                 if Delay < 0.0001 then
                     Delay = 0.0001
                 end
             end
             
             local closeRangeHitboxSize = 0.9
             local midRangeHitboxSize = 0.6
             local farRangeHitboxSize = 0.3
             
             local AllBodyParts = {
                 "Head", "UpperTorso", "LowerTorso", "HumanoidRootPart", "LeftHand", "RightHand", 
                 "LeftLowerArm", "RightLowerArm", "LeftUpperArm", "RightUpperArm", "LeftFoot", 
                 "LeftLowerLeg", "LeftUpperLeg", "RightLowerLeg", "RightUpperLeg", "RightFoot"
             }
             
             local function mouse1click(x, y)
                 VirtualInputManager:SendMouseButtonEvent(x, y, 0, true, game, false)
                 VirtualInputManager:SendMouseButtonEvent(x, y, 0, false, game, false)
             end
             
             local function getMousePosition()
                 local mouse = UserInputService:GetMouseLocation()
                 return mouse.X, mouse.Y
             end
             
             local function isWithinBox(position)
                 local screenPos = Camera:WorldToViewportPoint(position)
                 local screenCenter = Vector2.new(Camera.ViewportSize.X / 2, Camera.ViewportSize.Y / 2)
                 return (Vector2.new(screenPos.X, screenPos.Y) - screenCenter).Magnitude <= Camera.ViewportSize.X / 2
             end
             
             local function isMouseOnTarget(targetPlayer)
                 local mouse = LocalPlayer:GetMouse()
                 return mouse.Target and mouse.Target:IsDescendantOf(targetPlayer.Character)
             end
             
             local function isIgnoringKnife()
                 local currentTool = LocalPlayer.Character:FindFirstChildOfClass("Tool")
                 if currentTool then
                     local toolName = currentTool.Name:lower()
                     return toolName == "knife" or toolName == "katana" or toolName == "[knife]" or toolName == "[katana]"
                 end
                 return false
             end
             
             local function getVelocity(player)
                 local humanoidRootPart = player.Character:FindFirstChild("HumanoidRootPart")
                 if humanoidRootPart then
                     local velocity = humanoidRootPart.AssemblyLinearVelocity
                     return velocity
                 end
                 return Vector3.zero
             end
             
             -- Prediction method similar to the first code you provided
             local function predictTargetPosition(targetPlayer, deltaTime)
                 local partToPredict = targetPlayer.Character:FindFirstChild("HumanoidRootPart")
                 if not partToPredict then return targetPlayer.Character.HumanoidRootPart.Position end
             
                 -- Get velocity and apply distortion (Prediction.X, Prediction.Y, Prediction.Z)
                 local velocity = getVelocity(targetPlayer)
                 local adjustedVelocity = Vector3.new(
                     velocity.X * Prediction.X,
                     velocity.Y * Prediction.Y,
                     velocity.Z * Prediction.Z
                 )
             
                 -- Predict future position based on velocity
                 local predictedPos = partToPredict.Position + adjustedVelocity * deltaTime
                 return predictedPos
             end
             
             local function calculateHitboxSize(distance)
                 if distance <= 18 then  
                     return closeRangeHitboxSize
                 elseif distance > 18 and distance <= 30 then 
                     return midRangeHitboxSize
                 else  
                     return farRangeHitboxSize
                 end
             end
             
             local function updateFOVBasedOnRange(distance)
                 if distance <= 18 then
                     return FOV.Close
                 elseif distance > 18 and distance <= 30 then
                     return FOV.Middle
                 else
                     return FOV.Far
                 end
             end
             
             local function aimAtTargetBody(targetPlayer)
                 for _, bodyPartName in pairs(AllBodyParts) do
                     local bodyPart = targetPlayer.Character:FindFirstChild(bodyPartName)
                     if bodyPart and bodyPart:IsDescendantOf(targetPlayer.Character) then
                         local targetPos = bodyPart.Position
                         local distance = (targetPos - Camera.CFrame.Position).Magnitude
             
                         local currentFOV = updateFOVBasedOnRange(distance)
                         local hitboxSize = calculateHitboxSize(distance)
             
                         -- Use the prediction function to calculate where the target will be
                         local predictedPos = predictTargetPosition(targetPlayer, 0.1)
                         local screenPos, onScreen = Camera:WorldToViewportPoint(predictedPos)
                         if onScreen and isWithinBox(predictedPos) then
                             local mousePos = Vector2.new(screenPos.X, screenPos.Y)
                             local adjustedMousePos = mousePos + Vector2.new(math.random(-hitboxSize * 7, hitboxSize * 7), math.random(-hitboxSize * 7, hitboxSize * 7))
                             VirtualInputManager:SendMouseMoveEvent(adjustedMousePos.X, adjustedMousePos.Y, game)
             
                             if os.clock() - lastClickTime >= Delay and not isIgnoringKnife() then
                                 lastClickTime = os.clock()
             
                                 local mouseX, mouseY = getMousePosition()
                                 mouse1click(mouseX, mouseY)
                             end
                         end
                     end
                 end
             end
             
             local function TriggerAction()
                 if TargetPlayer and TargetPlayer.Character then
                     local humanoid = TargetPlayer.Character:FindFirstChild("Humanoid")
                     if humanoid and humanoid.Health > 0 then
                         if isMouseOnTarget(TargetPlayer) then
                             aimAtTargetBody(TargetPlayer)
                         end
                     end
                 end
             end
             
             UserInputService.InputBegan:Connect(function(input, gameProcessed)
                 if not gameProcessed and input.KeyCode.Name == Key then
                     if Mode == "toggle" then
                         isToggled = not isToggled  
                     elseif Mode == "hold" then
                         isToggled = true
                     end
                 end
             end)
             
             UserInputService.InputEnded:Connect(function(input, gameProcessed)
                 if not gameProcessed and input.KeyCode.Name == Key and Mode == "hold" then
                     isToggled = false  
                 end
             end)
             
             RunService.RenderStepped:Connect(function()
                 if TriggerEnabled and isToggled then  
                     TriggerAction()  
                 end
             end)
             
             
             
             
             
             
             local Players = game:GetService("Players")
             local LocalPlayer = Players.LocalPlayer
             local Mouse = LocalPlayer:GetMouse()
             local RunService = game:GetService("RunService")
             local Camera = game.Workspace.CurrentCamera
             
             local FOV = shared.Global.Camera.FOV
             
             local isRightMouseButtonDown = false  
             
             local whitelist = shared.Global.Core.Checks.Whitelist
             
             -- Threshold for determining first-person (the camera is close to the character)
             local FIRST_PERSON_THRESHOLD = 5 -- Camera within 5 studs of the player is considered first-person
             local THIRD_PERSON_THRESHOLD = 10 -- Camera beyond 10 studs is considered third-person
             
             -- Function to determine if the player is in first-person mode
             local function IsFirstPerson()
                 local playerPosition = LocalPlayer.Character and LocalPlayer.Character:FindFirstChild("HumanoidRootPart")
                 if playerPosition then
                     local cameraPosition = Camera.CFrame.Position
                     local distance = (cameraPosition - playerPosition.Position).Magnitude
                     return distance < FIRST_PERSON_THRESHOLD
                 end
                 return false
             end
             
             -- Function to determine if the player is in third-person mode
             local function IsThirdPerson()
                 local playerPosition = LocalPlayer.Character and LocalPlayer.Character:FindFirstChild("HumanoidRootPart")
                 if playerPosition then
                     local cameraPosition = Camera.CFrame.Position
                     local distance = (cameraPosition - playerPosition.Position).Magnitude
                     return distance > THIRD_PERSON_THRESHOLD
                 end
                 return false
             end
             
             local function UpdateFOV()
                 -- You can update the FOV here if needed, but it's not necessary for the logic
             end
             
             RunService.RenderStepped:Connect(UpdateFOV)
             
             local function ClosestPlrFromMouse()
                 local Target, Closest = nil, math.huge
                 local enabled = shared.Global.Core.Enabled  
                 local whitelist = shared.Global.Core.Checks.Whitelist  
             
                 for _, player in pairs(Players:GetPlayers()) do
                     if player ~= LocalPlayer and player.Character and player.Character:FindFirstChild("HumanoidRootPart") then
                         if enabled and table.find(whitelist, player.Name) then
                             break
                         end
             
                         local Position, OnScreen = Camera:WorldToScreenPoint(player.Character.HumanoidRootPart.Position)
                         local Distance = (Vector2.new(Position.X, Position.Y) - Vector2.new(Mouse.X, Mouse.Y)).Magnitude
             
                         if Distance < Closest and OnScreen then
                             Closest = Distance
                             Target = player
                         end
                     end
                 end
                 return Target
             end
             
             local function GetClosestBodyPart(character)
                 local ClosestDistance = math.huge
                 local BodyPart = nil
             
                 if character and character:IsDescendantOf(game.Workspace) then
                     for _, part in ipairs(character:GetChildren()) do
                         if part:IsA("BasePart") then
                             local Position, OnScreen = Camera:WorldToScreenPoint(part.Position)
                             if OnScreen then
                                 local Distance = (Vector2.new(Position.X, Position.Y) - Vector2.new(Mouse.X, Mouse.Y)).Magnitude
                                 if Distance < ClosestDistance then
                                     ClosestDistance = Distance
                                     BodyPart = part
                                 end
                             end
                         end
                     end
                 end
                 return BodyPart
             end
             
             local function GetTarget()
                 return TargetPlayer
             end
             
             Mouse.Button2Down:Connect(function()
                 if shared.Global.Camera.MouseButton2 then
                     isRightMouseButtonDown = true
                 end
             end)
             
             Mouse.Button2Up:Connect(function()
                 isRightMouseButtonDown = false
             end)
             
             -- Main targeting logic
             Mouse.KeyDown:Connect(function(Key)
                 local key = Key:lower()
             
                 if key == shared.Global.Core.Keybind:lower() then
                     if shared.Global.Camera.Enabled then
                         if IsTargeting then
                             if TargetPlayer and TargetPlayer.Character and TargetPlayer.Character:FindFirstChildOfClass("Humanoid") then
                                 if TargetPlayer.Character.Humanoid.Health >= 1 then
                                     if ClosestPlrFromMouse() == TargetPlayer then
                                         -- Already targeting the closest player
                                     else
                                         local newTarget = ClosestPlrFromMouse()
                                         if newTarget and newTarget.Character and newTarget.Character:FindFirstChildOfClass("Humanoid").Health >= 1 then
                                             TargetPlayer = newTarget
                                         end
                                     end
                                 else
                                     TargetPlayer = nil
                                     IsTargeting = false
                                 end
                             end
                         else
                             local initialTarget = ClosestPlrFromMouse()
                             if initialTarget and initialTarget.Character and initialTarget.Character:FindFirstChildOfClass("Humanoid").Health >= 1 then
                                 IsTargeting = true
                                 TargetPlayer = initialTarget
                             end
                         end
                     end
                 end
             
                 if key == shared.Global.Core.Cancel:lower() then  
                     IsTargeting = false
                     TargetPlayer = nil
                 end
             end)
             
             local function IsAlignedWithCamera(targetPlayer)
                 if targetPlayer and targetPlayer.Character then
                     local targetPosition = targetPlayer.Character.HumanoidRootPart.Position
                     local cameraPosition = Camera.CFrame.Position
                     local direction = (targetPosition - cameraPosition).unit
                     local targetDirection = Camera.CFrame.LookVector.unit
                     return direction:Dot(targetDirection) > 0.9
                 end
                 return false
             end
             
             local function GetDistanceFromMouse(bodyPart)
                 local mousePosition = game:GetService("Players").LocalPlayer:GetMouse().Hit.p
                 return (bodyPart.Position - mousePosition).Magnitude
             end
             
             RunService.RenderStepped:Connect(function()
                 if IsTargeting and TargetPlayer and TargetPlayer.Character then
                     if TargetPlayer.Character:FindFirstChildOfClass("Humanoid") and TargetPlayer.Character.Humanoid.Health < 2 then
                         TargetPlayer = nil
                         IsTargeting = false
                         return
                     end
             
                     if shared.Global.Camera.Enabled then
                         if shared.Global.Camera.MouseButton2 then
                             if isRightMouseButtonDown then
                                 if shared.Global.Camera.Configurations.ThirdPerson == false then
                                     if IsFirstPerson() then
                                         if IsAlignedWithCamera(TargetPlayer) then
                                             local head = TargetPlayer.Character:FindFirstChild("Head")
                                             local lowerTorso = TargetPlayer.Character:FindFirstChild("LowerTorso")
             
                                             local bodyPart = nil
                                             if head and lowerTorso then
                                                 local distanceToHead = GetDistanceFromMouse(head)
                                                 local distanceToLowerTorso = GetDistanceFromMouse(lowerTorso)
             
                                                 if distanceToHead < distanceToLowerTorso then
                                                     bodyPart = head
                                                 else
                                                     bodyPart = lowerTorso
                                                 end
                                             elseif head then
                                                 bodyPart = head
                                             elseif lowerTorso then
                                                 bodyPart = lowerTorso
                                             end
             
                                             if bodyPart then
                                                 local targetPosition = bodyPart.Position
                                                 local playerPosition = TargetPlayer.Character.HumanoidRootPart.Position
                                                 local distanceToTarget = (targetPosition - playerPosition).Magnitude
             
                                                 if distanceToTarget <= math.sqrt(FOV.X^2 + FOV.Y^2 + FOV.Z^2) then
                                                     local predictedPosition
                                                     if shared.Global.Camera.Resolver then
                                                         local humanoid = TargetPlayer.Character:FindFirstChildOfClass("Humanoid")
                                                         if humanoid then
                                                             local moveDirection = humanoid.MoveDirection
                                                             predictedPosition = bodyPart.Position + (moveDirection * Vector3.new(
                                                                 shared.Global.Camera.Prediction.X,
                                                                 shared.Global.Camera.Prediction.Y,
                                                                 shared.Global.Camera.Prediction.Z
                                                             ))
                                                         end
                                                     else
                                                         local targetVelocity = TargetPlayer.Character.HumanoidRootPart.Velocity
                                                         predictedPosition = bodyPart.Position + (targetVelocity * Vector3.new(
                                                             shared.Global.Camera.Prediction.X,
                                                             shared.Global.Camera.Prediction.Y,
                                                             shared.Global.Camera.Prediction.Z
                                                         ))
                                                     end
             
                                                     if predictedPosition then
                                                         local DesiredCFrame = CFrame.new(Camera.CFrame.Position, predictedPosition)
                                                         Camera.CFrame = Camera.CFrame:Lerp(DesiredCFrame, shared.Global.Camera.Configurations.Value)
                                                     end
                                                 end
                                             end
                                         end
                                     end
                                 end
                             end
                         else
                             if shared.Global.Camera.Configurations.ThirdPerson == false then
                                 if IsFirstPerson() then
                                     if IsAlignedWithCamera(TargetPlayer) then
                                         local head = TargetPlayer.Character:FindFirstChild("Head")
                                         local lowerTorso = TargetPlayer.Character:FindFirstChild("LowerTorso")
             
                                         local bodyPart = nil
                                         if head and lowerTorso then
                                             local distanceToHead = GetDistanceFromMouse(head)
                                             local distanceToLowerTorso = GetDistanceFromMouse(lowerTorso)
             
                                             if distanceToHead < distanceToLowerTorso then
                                                 bodyPart = head
                                             else
                                                 bodyPart = lowerTorso
                                             end
                                         elseif head then
                                             bodyPart = head
                                         elseif lowerTorso then
                                             bodyPart = lowerTorso
                                         end
             
                                         if bodyPart then
                                             local targetPosition = bodyPart.Position
                                             local playerPosition = TargetPlayer.Character.HumanoidRootPart.Position
                                             local distanceToTarget = (targetPosition - playerPosition).Magnitude
             
                                             if distanceToTarget <= math.sqrt(FOV.X^2 + FOV.Y^2 + FOV.Z^2) then
                                                 local predictedPosition
                                                 if shared.Global.Camera.Resolver then
                                                     local humanoid = TargetPlayer.Character:FindFirstChildOfClass("Humanoid")
                                                     if humanoid then
                                                         local moveDirection = humanoid.MoveDirection
                                                         predictedPosition = bodyPart.Position + (moveDirection * Vector3.new(
                                                             shared.Global.Camera.Prediction.X,
                                                             shared.Global.Camera.Prediction.Y,
                                                             shared.Global.Camera.Prediction.Z
                                                         ))
                                                     end
                                                 else
                                                     local targetVelocity = TargetPlayer.Character.HumanoidRootPart.Velocity
                                                     predictedPosition = bodyPart.Position + (targetVelocity * Vector3.new(
                                                         shared.Global.Camera.Prediction.X,
                                                         shared.Global.Camera.Prediction.Y,
                                                         shared.Global.Camera.Prediction.Z
                                                     ))
                                                 end
             
                                                 if predictedPosition then
                                                     local DesiredCFrame = CFrame.new(Camera.CFrame.Position, predictedPosition)
                                                     Camera.CFrame = Camera.CFrame:Lerp(DesiredCFrame, shared.Global.Camera.Configurations.Value)
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
             
             
             
            
             
             
             
             
             
             
             
             
             
             
             
             
             
             local G                   = game
             local Run_Service         = G:GetService("RunService")
             local Players             = G:GetService("Players")
             local UserInputService    = G:GetService("UserInputService")
             local Local_Player        = Players.LocalPlayer
             local Mouse               = Local_Player:GetMouse()
             local Current_Camera      = G:GetService("Workspace").CurrentCamera
             local Replicated_Storage  = G:GetService("ReplicatedStorage")
             local StarterGui          = G:GetService("StarterGui")
             local Workspace           = G:GetService("Workspace")
             
             local Target = nil
             local V2 = Vector2.new
             local holdingMouseButton = false
             local lastToolUse = 0
             local FovParts = {}
             
             
             if not game:IsLoaded() then
             game.Loaded:Wait()
             end
             
             
             local Games = {
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
             ID = 127504606438871,
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
             
             
             local gameId = game.PlaceId
             local gameSettings
             
             
             for _, gameData in pairs(Games) do
             if gameData.ID == gameId then
             gameSettings = gameData.Details
             break
             end
             end
             
             if not gameSettings then
             Players.LocalPlayer:Kick("Unsupported game")
             return
             end
             
             local RemoteEvent = gameSettings.Remote
             local Argument = gameSettings.Argument
             local BodyEffects = gameSettings.BodyEffects or "K.O"
             
             
             local ReplicatedStorage = game:GetService("ReplicatedStorage")
             local MainEvent = ReplicatedStorage:FindFirstChild(RemoteEvent)
             
             if not MainEvent then
             Players.LocalPlayer:Kick("Are you sure this is the correct game?")
             return
             end
             
             local function isArgumentValid(argumentName)
             return argumentName == Argument
             end
             
             local argumentToCheck = Argument
             
             if isArgumentValid(argumentToCheck) then
             MainEvent:FireServer(argumentToCheck)
             else
             Players.LocalPlayer:Kick("Invalid argument")
             end
             
             
             local function clearFovParts()
             
             for _, part in ipairs(FovParts) do
             part:Destroy()
             end
             FovParts = {}  
             end
             
             
             local scalingFactor = 35 / 5.5
             
             local function calculateFov(X, Y, Z)
                 local baseSize = 3.5  
                 local baseFov = 12   
                 
                 local sizeProduct = X * Y * Z
                 local calculatedFov = baseFov * (sizeProduct / (baseSize * baseSize * baseSize))
                 
                 return calculatedFov
             end
             
             local function getCurrentWeapon()
                 local character = Local_Player.Character
                 if not character then return nil end
             
                 local tool = character:FindFirstChildOfClass("Tool")
                 if tool then
                     return tool.Name
                 end
             
                 return nil
             end
             
             local function updateFov()
                 local currentWeapon = getCurrentWeapon()
                 local dynamicFovSize = 3.5 
             
                 if IsTargeting then
                     
                     if currentWeapon == "db" then
                         dynamicFovSize = shared.Global['Silent'].FOV['Double-Barrel SG'] or 3.5
                     elseif currentWeapon == "rev" then
                         dynamicFovSize = shared.Global['Silent'].FOV['Revolver'] or 3.5
                     elseif currentWeapon == "Tactical SG" then
                         dynamicFovSize = shared.Global['Silent'].FOV['Tactical SG'] or 3.5
                     end
             
                     
                     dynamicFovSize = dynamicFovSize * scalingFactor
             
                     
                     for _, player in pairs(Players:GetPlayers()) do
                         if player.Character and player ~= Local_Player then
                             local success, isDead = pcall(function() return Death(player) end)
             
                             if success and not isDead then
                                 local closestPart, closestPoint = pcall(function() return GetClosestHitPoint(player.Character) end)
             
                                 if closestPart and closestPoint then
                                     local screenPoint = Current_Camera:WorldToScreenPoint(closestPoint)
                                     local distance = (V2(screenPoint.X, screenPoint.Y) - V2(Mouse.X, Mouse.Y)).Magnitude
             
                                     if distance <= dynamicFovSize then
                                         
                                     end
                                 end
                             end
                         end
                     end
                 end
             end
             
             Run_Service.RenderStepped:Connect(updateFov)
             
             local function isPartInFovAndVisible(part)
                 if not shared.Global.Camera.Enabled or not IsTargeting or not TargetPlayer then
                     return false
                 end
             
                 local currentWeapon = getCurrentWeapon()
                 local dynamicFovSize = 3.5  
             
                 if currentWeapon == "db" then
                     dynamicFovSize = shared.Global['Silent'].FOV['Double-Barrel SG'] or 3.5
                 elseif currentWeapon == "rev" then
                     dynamicFovSize = shared.Global['Silent'].FOV['Revolver'] or 3.5
                 elseif currentWeapon == "Tactical SG" then
                     dynamicFovSize = shared.Global['Silent'].FOV['Tactical SG'] or 3.5
                 end
             
                 dynamicFovSize = dynamicFovSize * scalingFactor
             
                 local screenPoint, onScreen = Current_Camera:WorldToScreenPoint(part.Position)
                 local distance = (V2(screenPoint.X, screenPoint.Y) - V2(Mouse.X, Mouse.Y)).Magnitude
             
                 return onScreen and distance <= dynamicFovSize
             end
             
             local function isPartVisible(part)
                 if not shared.Global['Silent'].WallCheck then
                     return true
                 end
                 local origin = Current_Camera.CFrame.Position
                 local direction = (part.Position - origin).Unit * (part.Position - origin).Magnitude
                 local ray = Ray.new(origin, direction)
                 local hit = Workspace:FindPartOnRayWithIgnoreList(ray, {Local_Player.Character, part.Parent})
                 return hit == part or not hit
             end
             
             
             
             
             
             local function GetClosestHitPoint(character)
             local closestPart = nil
             local closestPoint = nil
             local shortestDistance = math.huge
             
             local AllBodyParts = {
             "Head", "UpperTorso", "LowerTorso", "HumanoidRootPart", "LeftHand", "RightHand", 
             "LeftLowerArm", "RightLowerArm", "LeftUpperArm", "RightUpperArm", "LeftFoot", 
             "LeftLowerLeg", "LeftUpperLeg", "RightLowerLeg", "RightUpperLeg", "RightFoot"
             }
             
             for _, bodyPartName in pairs(AllBodyParts) do
             local part = character:FindFirstChild(bodyPartName)
             
             if part and part:IsA("BasePart") and isPartInFovAndVisible(part) and isPartVisible(part) then
               local screenPoint, onScreen = Current_Camera:WorldToScreenPoint(part.Position)
               local distance = (V2(screenPoint.X, screenPoint.Y) - V2(Mouse.X, Mouse.Y)).Magnitude
             
               if distance < shortestDistance then
                   closestPart = part
                   closestPoint = part.Position  
                   shortestDistance = distance
               end
             end
             end
             
             return closestPart, closestPoint
             end
             
             
             local OldTime = shared.Global['Silent'].Prediction

             local function GetVelocity(player, part)
                 if player and player.Character then
                     local velocity = player.Character[part].Velocity
             
                     
                     local distortionX = shared.Global['Silent'].Prediction.X
                     local distortionY = shared.Global['Silent'].Prediction.Y
                     local distortionZ = shared.Global['Silent'].Prediction.Z
             
                    
                     local adjustedVelocity = Vector3.new(
                         velocity.X * distortionX,
                         velocity.Y * distortionY,
                         velocity.Z * distortionZ
                     )
             
                     
                     if adjustedVelocity.Y < -30 then
                         shared.Global['Silent'].Prediction = { X = 0, Y = 0, Z = 0 }
                         return adjustedVelocity
                  
                     elseif adjustedVelocity.Magnitude > 50 then
                         return player.Character:FindFirstChild("Humanoid").MoveDirection * 16 * distortionX
                     else
                         
                         shared.Global['Silent'].Prediction = OldTime
                         return adjustedVelocity
                     end
                 end
                 return Vector3.new(0, 0, 0)  
             end
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             UserInputService.InputEnded:Connect(function(input, isProcessed)
                 if input.KeyCode == Enum.KeyCode[shared.Global.Core.Keybind:upper()] and shared.Global.Camera.Method == "hold" then  -- Updated to Keybind
                     holdingMouseButton = false
                 end
             end)
             
             
             local function clearFovParts()
             for _, part in ipairs(FovParts) do
             part:Destroy()
             end
             FovParts = {}
             end
             
             local LastTarget = nil
             
             
             local function IsVisible(targetPosition)
                local character = game.Players.LocalPlayer.Character
                if not character then return false end
             
                local origin = character.Head.Position
                local direction = (targetPosition - origin).Unit * 1000
             
                local rayParams = RaycastParams.new()
                rayParams.FilterType = Enum.RaycastFilterType.Blacklist
                rayParams.FilterDescendantsInstances = {character}
             
                local success, raycastResult = pcall(function()
                    return workspace:Raycast(origin, direction, rayParams)
                end)
             
                return success and raycastResult and (raycastResult.Position - targetPosition).Magnitude < 5
             end
             
             RunService.RenderStepped:Connect(function()
                local character = game.Players.LocalPlayer.Character
                if character and character:FindFirstChild("Humanoid") then
                    local humanoid = character.Humanoid
             
                  
                    local success, _ = pcall(function()
                        if humanoid.Health <= 1 then
                            TargetPlayer = nil
                            IsTargeting = false
                            LastTarget = nil
                            return
                        end
                    end)
                end
             
                if shared.Global['Silent'].Enabled and IsTargeting then
                    if TargetPlayer then
                        if TargetPlayer.Character then
                            local targetPos = TargetPlayer.Character.Head.Position
             
                          
                            local success, _ = pcall(function()
                                if TargetPlayer.Character.Humanoid.Health < 1 then
                                    TargetPlayer = nil
                                    IsTargeting = false
                                    LastTarget = nil
                                    return
                                end
                            end)
             
                          
                            local success2, _ = pcall(function()
                                if Death(TargetPlayer) then
                                    TargetPlayer = nil
                                    IsTargeting = false
                                    LastTarget = nil
                                    return
                                end
                            end)
             
                          
                            local success3, closestPart, closestPoint = pcall(function()
                                if not IsVisible(targetPos) then
                                    IsTargeting = false
                                    LastTarget = TargetPlayer
                                    return
                                end
                                return GetClosestHitPoint(TargetPlayer.Character)
                            end)
             
                            if success3 and closestPart and closestPoint then
                                local velocity = GetVelocity(TargetPlayer, closestPart.Name)
             
                                local TimeX = shared.Global['Silent'].Prediction.X
                                local TimeY = shared.Global['Silent'].Prediction.Y
                                local TimeZ = shared.Global['Silent'].Prediction.Z
                                
                                local adjustedVelocity = velocity * Vector3.new(TimeX, TimeY, TimeZ)
             
                              
                                local success4, _ = pcall(function()
                                    Replicated_Storage[RemoteEvent]:FireServer(Argument, closestPoint + adjustedVelocity)
                                end)
                            end
                        end
                    end
                elseif LastTarget and LastTarget.Character then
                    local lastTargetPos = LastTarget.Character.Head.Position
             
                   
                    local success5, _ = pcall(function()
                        if IsVisible(lastTargetPos) then
                            TargetPlayer = LastTarget
                            IsTargeting = true
                            LastTarget = nil
                        end
                    end)
                else
                 
                end
             end)
             
             task.spawn(function()
                while task.wait(0.1) do
                    if shared.Global['Silent'].Enabled then
                     
                    end
                end
             end)
             
             
             
             
             
             local function HookTool(tool)
                if tool:IsA("Tool") then
                    tool.Activated:Connect(function()
                        if tick() - lastToolUse > 0.1 then  
                            lastToolUse = tick()
             
                           
                            local success, target = pcall(function()
                                return TargetPlayer
                            end)
             
                            if success and target and target.Character then
                              
                                local success2, closestPart, closestPoint = pcall(function()
                                    return GetClosestHitPoint(target.Character)
                                end)
             
                                if success2 and closestPart and closestPoint then
                                 
                                    local success3, velocity = pcall(function()
                                        return GetVelocity(target, closestPart.Name)
                                    end)
             
                                    if success3 and velocity then
                                        local TimeX = shared.Global['Silent'].Prediction.X
                                        local TimeY = shared.Global['Silent'].Prediction.Y
                                        local TimeZ = shared.Global['Silent'].Prediction.Z
                                        
                                        local adjustedVelocity = velocity * Vector3.new(TimeX, TimeY, TimeZ)
                                        
                                        
                                        local success4, _ = pcall(function()
                                            Replicated_Storage[RemoteEvent]:FireServer(Argument, closestPoint + adjustedVelocity)
                                        end)
                                    end
                                end
                            end
                        end
                    end)
                end
             end
             
             local function onCharacterAdded(character)
                character.ChildAdded:Connect(function(child)
                    
                    local success, _ = pcall(function()
                        HookTool(child)
                    end)
                end)
                
                
                for _, tool in pairs(character:GetChildren()) do
                    local success, _ = pcall(function()
                        HookTool(tool)
                    end)
                end
             end
             
             
             local success, _ = pcall(function()
                Local_Player.CharacterAdded:Connect(onCharacterAdded)
             end)
             
             
             if Local_Player.Character then
                pcall(function()
                    onCharacterAdded(Local_Player.Character)
                end)
             end
             
             
             
             
             end
             
             
             if table.find(gameIdsToWaitFor, currentGameId) then
             local settingsFrame = findSettingsFrame(playerGui)
             
             if settingsFrame then
             settingsFrame:GetPropertyChangedSignal("Visible"):Connect(function()
              if settingsFrame.Visible and not scriptExecuted then
                  scriptExecuted = true
                  handleSettingsOpened()  
              end
             end)
             else
             
             handleSettingsOpened()
             end
             else
             
             handleSettingsOpened()
             end
             
         end
